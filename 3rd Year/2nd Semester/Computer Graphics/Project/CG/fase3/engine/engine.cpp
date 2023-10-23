#include <stdlib.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <fstream>
#include "tinyxml2.h"
#include "point.hpp"
#include "projection.hpp"
#include "extras.hpp"

using namespace tinyxml2;

struct WinDimensions {
	int width;
	int height;
};

struct camPos {
    Point pos;
    Point up = Point(0, 1, 0);
    Projection proj;
    double dirAlpha = -M_PI_2, dirBeta = -M_PI_2;
};

struct translation {
    int time;
    bool align;
    std::vector<Point> points;
};

struct groupC {
    std::vector<string> figS;
    translation translate;
    std::vector<string> trans;
    double transR[4];
    double transT[3];
    double transS[3];
	double color[3] = { 255.0, 255.0, 255.0};
    std::vector<groupC> sub_groups;
};

camPos cam;
groupC groups;
WinDimensions winDimensions;
bool mov[6] { false };
bool fps = false;
int startX = 0, startY = 0;
std::map<string, std::pair<int, GLuint>> figVBO;

Point camLookAtPoint() {
    return Point(cam.pos.x() + getX(1, cam.dirAlpha, cam.dirBeta),
                 cam.pos.y() + getY(1, cam.dirBeta),
                 cam.pos.z() + getZ(1, cam.dirAlpha, cam.dirBeta));
}

std::vector<double> read3d(string filename) {
    string file = "./" + filename;

    FILE* fp = fopen(file.c_str(), "r");
    char s[100];

    std::vector<double> fig;

    if (!fp) {
		printf("Erro ao abrir o ficheiro: %s", filename);
        exit(1);
    }

    else {
        fgets(s, 100, fp);
        int num = stoi(s);

        for (int i = 0; i < num; i++) {
            double x = 0.0;
            double y = 0.0;
            double z = 0.0;
            fscanf(fp, "%lf;%lf;%lf", &x, &y, &z);

            fig.push_back(x);
            fig.push_back(y);
            fig.push_back(z);

        }

        fig;
    }

    return fig;
}

void parsePoint(XMLElement* elemento, Point* ponto) {
    double x, y, z;

    if (elemento->Attribute("x"))
        x = stof(elemento->Attribute("x"));
    if (elemento->Attribute("y"))
        y = stof(elemento->Attribute("y"));
    if (elemento->Attribute("z"))
        z = stof(elemento->Attribute("z"));

    *ponto = Point(x, y, z);
}

void parseProjection(XMLElement* elemento, Projection* projection) {
    if (elemento->Attribute("fov"))
        projection->setFov(stof(elemento->Attribute("fov")));
    if (elemento->Attribute("near"))
        projection->setNear(stof(elemento->Attribute("near")));
    if (elemento->Attribute("far"))
        projection->setFar(stof(elemento->Attribute("far")));
}

void parseWindow(XMLElement* root) {
	XMLElement* winElement = root->FirstChildElement("window");
	if (winElement) {
		
		if (winElement->Attribute("width")) {
			winDimensions.width = stoi(winElement->Attribute("width"));
		}

		if (winElement->Attribute("height")) {
			winDimensions.height = stoi(winElement->Attribute("height"));
		}
	}
}

void parseCamera(XMLElement* root) {
    if (!root) {
		printf("Erro");
    }

    XMLElement* camera = root->FirstChildElement("camera");

    if (camera) {

        XMLElement* posXml = camera->FirstChildElement("position");
        if (posXml) {
            parsePoint(posXml, &cam.pos);
        }

        XMLElement* lookAtXml = camera->FirstChildElement("lookAt");
        if (lookAtXml) {
            Point p;
            parsePoint(lookAtXml, &p);
            double x = p.x() - cam.pos.x();
            double y = p.y() - cam.pos.y();
            double z = p.z() - cam.pos.z();
            double r = sqrt(x * x + y * y + z * z);
            cam.dirAlpha = M_PI + atan(x / z);
            cam.dirBeta = asin(y / r);
            if (cam.dirBeta > M_PI_2)
                cam.dirBeta -= M_PI;
        }

        XMLElement* upXml = camera->FirstChildElement("up");
        if (upXml) {
            parsePoint(upXml, &cam.up);
        }

        XMLElement* projXml = camera->FirstChildElement("projection");
        if (projXml) {
            parseProjection(projXml, &cam.proj);
        }
    }
}

groupC parseGroup(XMLElement* group) {
    groupC g;
    XMLElement* elem = group->FirstChildElement();

    while (elem) {
        if (!strcmp(elem->Name(), "models")) {
            XMLElement* model = elem->FirstChildElement();
            
            string file = model->Attribute("file");

            if (figVBO.find(file) == figVBO.end()) {
                std::vector<double> fig = read3d(file);

                int size = fig.size();

                GLuint vbo;
                glGenBuffers(1, &vbo);
                glBindBuffer(GL_ARRAY_BUFFER, vbo);
                glBufferData(GL_ARRAY_BUFFER, size * sizeof(double), fig.data(), GL_STATIC_DRAW);

                figVBO.insert(std::pair<string, std::pair<int, GLuint>>(file, std::pair<int, GLuint>(size,vbo)));
            }

            g.figS.push_back(file);
        }

        else if (!strcmp(elem->Name(), "transform")) {
            XMLElement* transform = elem->FirstChildElement();
            while (transform){
                if (!strcmp(transform->Name(), "translate")) {
                    if (!transform->NoChildren()) {
                        g.trans.push_back("translateCR");
                        g.translate.time = transform->DoubleAttribute("time");
                        g.translate.align = transform->BoolAttribute("align");

                        XMLElement* translate = transform->FirstChildElement();
                        while (translate) {
                            Point p = Point(translate->DoubleAttribute("x"), translate->DoubleAttribute("y"), translate->DoubleAttribute("z"));
                            g.translate.points.push_back(p);
                            translate = translate->NextSiblingElement();
                        }
                    }

                    else {
                        g.trans.push_back("translate");
                        g.transT[0] = transform->DoubleAttribute("x");
                        g.transT[1] = transform->DoubleAttribute("y");
                        g.transT[2] = transform->DoubleAttribute("z");
                    }
                }

                else if (!strcmp(transform->Name(), "rotate")) {
                    if (transform->FindAttribute("angle")) {
                        g.trans.push_back("rotateA");
                        g.transR[0] = transform->DoubleAttribute("angle");
                    }

                    else {
                        g.trans.push_back("rotateT");
                        g.transR[0] = transform->DoubleAttribute("time");
                    }

                    g.transR[1] = transform->DoubleAttribute("x");
                    g.transR[2] = transform->DoubleAttribute("y");
                    g.transR[3] = transform->DoubleAttribute("z");
                }

                else if (!strcmp(transform->Name(), "scale")) {
                    g.trans.push_back("scale");
                    g.transS[0] = transform->DoubleAttribute("x");
                    g.transS[1] = transform->DoubleAttribute("y");
                    g.transS[2] = transform->DoubleAttribute("z");
                }

 	           else if (!strcmp(transform->Name(), "color")) {
                    g.color[0] = transform->DoubleAttribute("R");
                    g.color[1] = transform->DoubleAttribute("G");
                    g.color[2] = transform->DoubleAttribute("B");
                }

                transform = transform->NextSiblingElement();
            }
        }

        else if (!strcmp(elem->Name(), "group")) {
            g.sub_groups.push_back(parseGroup(elem));
        }

        elem = elem->NextSiblingElement();
    }

    return g;
}

void readXML(char* filename) {
    XMLDocument docxml;

    string file = "./" + string(filename);

    XMLError erro = docxml.LoadFile(file.c_str());

    if (erro != XML_SUCCESS) {
        printf("Error: %i\n", erro);
		printf("Ficheiro XML não foi encontrado.");
    }

    else {
        XMLElement* root = docxml.FirstChildElement("world");
		parseWindow(root);
        parseCamera(root);
        groups = parseGroup(root);
    }
}

void axis() {
    glBegin(GL_LINES);

    // x axis in red
    glColor3f(1.0f, 0.0f, 0.0f);
	glVertex3f(-700.0f, 0.0f, 0.0f);
    glVertex3f(700.0f, 0.0f, 0.0f);
    
	// y axis in green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -700.0f, 0.0f);
    glVertex3f(0.0f, 700.0f, 0.0f);

	// z axis in blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -700.0f);
    glVertex3f(0.0f, 0.0f, 700.0f);

	glEnd();

    glColor3f(1,1,1);
}

void changeSize(int w, int h) {

    // Prevent a divide by zero, when window is too short
    // (you can’t make a window with zero width).
    if (h == 0)
        h = 1;
    // compute window's aspect ratio
    double ratio = w * 1.0f / h;
    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load the identity matrix
    glLoadIdentity();
    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);
    // Set the perspective
    gluPerspective(cam.proj.getFov(), ratio, cam.proj.getNear(), cam.proj.getFar());
    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}

void getGlobalCatmullRomPoint(std::vector<Point> p, double gt, double* pos, double* deriv) {
    int pointCount = p.size();

    double t = gt * pointCount;
    int index = floor(t);
    t = t - index;

    int p0 = (index + pointCount - 1) % pointCount;
    int p1 = (p0 + 1) % pointCount;
    int p2 = (p1 + 1) % pointCount;
    int p3 = (p2 + 1) % pointCount;

    getCatmullRomPoint(t, p[p0], p[p1], p[p2], p[p3], pos, deriv);
}

void renderCatmullRomCurve(std::vector<Point> p) {
    double pos[3];
    double deriv[3];

    glBegin(GL_LINE_LOOP);
    for (double gt = 0.f; 1.f > gt; gt += 0.01f) {
        getGlobalCatmullRomPoint(p, gt, pos, deriv);
        glVertex3f(pos[0], pos[1], pos[2]);
    }
    glEnd();
}

void renderGroup(groupC group) {
    glPushMatrix();

    double t = glutGet(GLUT_ELAPSED_TIME) / 1000.0;

    for (int gt = 0; gt < group.trans.size(); gt++) {
        if (!strcmp(group.trans[gt].c_str(), "rotateA"))
            glRotated(group.transR[0], group.transR[1], group.transR[2], group.transR[3]);
        else if (!strcmp(group.trans[gt].c_str(), "rotateT"))
            glRotated(360 * t / group.transR[0], group.transR[1], group.transR[2], group.transR[3]);
        else if (!strcmp(group.trans[gt].c_str(), "scale"))
            glScaled(group.transS[0], group.transS[1], group.transS[2]);
        else if (!strcmp(group.trans[gt].c_str(), "translate"))
            glTranslated(group.transT[0], group.transT[1], group.transT[2]);
        else if (!strcmp(group.trans[gt].c_str(), "translateCR")) {
            renderCatmullRomCurve(group.translate.points);

            double pos[3];
            double x[3], z[3];
            static double yLast[3] = { 0, 1, 0 };
            float m[16];

            getGlobalCatmullRomPoint(group.translate.points, t / group.translate.time, pos, x);
            glTranslatef(pos[0], pos[1], pos[2]);
            
            if (group.translate.align) {
                cross(x, yLast, z);
                cross(z, x, yLast);
                normalize(x);
                normalize(yLast);
                normalize(z);

                buildRotMatrix(x, yLast, z, m);
                glMultMatrixf(m);
            }
        }
    }

    for (int fig = 0; fig < group.figS.size(); fig++) {
        std::pair<int, GLuint> figure = figVBO.find(group.figS[fig])->second;
        glBindBuffer(GL_ARRAY_BUFFER, figure.second);
        glVertexPointer(3, GL_DOUBLE, 0, 0);

        glColor3f(group.color[0] / 255.0, group.color[1] / 255.0, group.color[2] / 255.0);

        glDrawArrays(GL_TRIANGLES, 0, figure.first);
    }

    for (int gsub = 0; gsub < group.sub_groups.size(); gsub++) {
        renderGroup(group.sub_groups[gsub]);
    }

    glPopMatrix();
}

void renderScene(void) {

    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set camera
    glLoadIdentity();

    Point lookAt = camLookAtPoint();
    gluLookAt(
        cam.pos.x(), cam.pos.y(), cam.pos.z(),
        lookAt.x(), lookAt.y(), lookAt.z(),
        cam.up.x(), cam.up.y(), cam.up.z()
    );

    // put drawing instructions here
    axis();

    renderGroup(groups);

    // End of frame
    glutSwapBuffers();
}

void keyboardFunc(unsigned char key, int x, int y) {
    float moveSpeed = 1.0f;

    switch (key) {
        case 'w':
            cam.pos = Point(cam.pos.x(), cam.pos.y(), cam.pos.z() - moveSpeed);
            //lookAt = Point(lookAt.x(), lookAt.y(), lookAt.z() - moveSpeed);
            break;
        case 's':
            cam.pos = Point(cam.pos.x(), cam.pos.y(), cam.pos.z() + moveSpeed);
            //lookAt = Point(lookAt.x(), lookAt.y(), lookAt.z() + moveSpeed);
            break;
        case 'a':
            cam.pos = Point(cam.pos.x() - moveSpeed, cam.pos.y(), cam.pos.z());
            //lookAt = Point(lookAt.x() - moveSpeed, lookAt.y(), lookAt.z());
            break;
        case 'd':
            cam.pos = Point(cam.pos.x() + moveSpeed, cam.pos.y(), cam.pos.z());
            //lookAt = Point(lookAt.x() + moveSpeed, lookAt.y(), lookAt.z());
            break;
        case 'q':
            cam.pos = Point(cam.pos.x(), cam.pos.y() - moveSpeed, cam.pos.z());
            //lookAt = Point(lookAt.x(), lookAt.y() - moveSpeed, lookAt.z());
            break;
        case 'e':
            cam.pos = Point(cam.pos.x(), cam.pos.y() + moveSpeed, cam.pos.z());
            //lookAt = Point(lookAt.x(), lookAt.y() + moveSpeed, lookAt.z());
            break;
    }

    // Redraw the scene with the updated camera position
    glutPostRedisplay();
}


int main(int argc, char** argv) {

    // put GLUT’s init here
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(winDimensions.width, winDimensions.height);
    glutCreateWindow("CG");

    // put callback registry here
    glutReshapeFunc(changeSize);
    glutIdleFunc(renderScene);
    glutDisplayFunc(renderScene);

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    // funcoes que queiram adicionar tipo rato ou teclas
	glutKeyboardFunc(keyboardFunc);

    #ifndef __APPLE__
    glewInit();
    #endif

    // some OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    glEnableClientState(GL_VERTEX_ARRAY);

    readXML(argv[1]);

    // enter GLUT’s main cycle
    glutMainLoop();

    return 1;
}
