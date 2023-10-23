#include <stdlib.h>
#include "projection.hpp"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>
#include <stdio.h>
#include <iostream>
#include <string>
#include <vector>
#include "tinyxml2.h"
#include "point.hpp"

using namespace tinyxml2;

struct WinDimensions {
	int width;
	int height;
};

struct camPos {
    Point pos = Point(2, 2, 3);
    Point lookAt = Point(0, 0, 0);
    Point up = Point(0, 1, 0);
    Projection proj;
};

struct groupC {
    std::vector<std::vector<Point>> shapes;
    std::vector<string> trans;
    double transR[4];
    double transT[3];
    double transS[3];
	double color[3];
    std::vector<groupC> sub_groups;
};

camPos cam;
groupC groups;
WinDimensions winDimensions;

std::vector<Point> read3d(string filename) {
    string file = "./" + filename;

    FILE* fp = fopen(file.c_str(), "r");
    char s[100];

    std::vector<Point> final;

    if (!fp) {
		printf("Erro ao abrir o ficheiro: %s", filename);
        exit(1);
    }

    else {
        fgets(s, 100, fp);
        int num = stoi(s);

        for (int i = 0; i < num; i++) {
            float x = 0.0;
            float y = 0.0;
            float z = 0.0;
            fscanf(fp, "%f;%f;%f", &x, &y, &z);
            Point p = Point(x, y, z);
            final.push_back(p);
        }

        return(final);
    }
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
            parsePoint(lookAtXml, &cam.lookAt);
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
    Point aux = Point(0, 0, 0);

    while (elem) {
        if (!strcmp(elem->Name(), "models")) {
            XMLElement* model = elem->FirstChildElement();
            while (model) {
                g.shapes.push_back(read3d(model->Attribute("file")));
                model = model->NextSiblingElement();
            }
        }

        else if (!strcmp(elem->Name(), "transform")) {
            XMLElement* transform = elem->FirstChildElement();
            while (transform){
                if (!strcmp(transform->Name(), "translate")) {
                    g.trans.push_back("translate");
                    g.transT[0] = transform->DoubleAttribute("x");
                    g.transT[1] = transform->DoubleAttribute("y");
                    g.transT[2] = transform->DoubleAttribute("z");
                }

                else if (!strcmp(transform->Name(), "rotate")) {
                    g.trans.push_back("rotate");
                    g.transR[0] = transform->DoubleAttribute("angle");
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
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
	// glVertex3f(-700.0f, 0.0f, 0.0f);
    // glVertex3f(700.0f, 0.0f, 0.0f);
    
	// y axis in green
    glColor3f(0.0f, 1.0f, 0.0f);
	glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    // glVertex3f(0.0f, -700.0f, 0.0f);
    // glVertex3f(0.0f, 700.0f, 0.0f);

	// z axis in blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    // glVertex3f(0.0f, 0.0f, -700.0f);
    // glVertex3f(0.0f, 0.0f, 700.0f);

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

void renderGroup(groupC group) {
    glPushMatrix();
    for (int gt = 0; gt < group.trans.size(); gt++) {
        if (!strcmp(group.trans[gt].c_str(), "translate"))
            glTranslated(group.transT[0], group.transT[1], group.transT[2]);
        else if (!strcmp(group.trans[gt].c_str(), "rotate"))
            glRotated(group.transR[0], group.transR[1], group.transR[2], group.transR[3]);
        else if (!strcmp(group.trans[gt].c_str(), "scale"))
            glScaled(group.transS[0], group.transS[1], group.transS[2]);
    }

	glColor3f(group.color[0] / 255.0, group.color[1] / 255.0, group.color[2] / 255.0);

    glBegin(GL_TRIANGLES);
    for (int gs = 0; gs < group.shapes.size(); gs++) {
        std::vector<Point> shape = group.shapes[gs];
        for (int sh = 0; sh < shape.size(); sh++) {
			glVertex3f(shape[sh].x(), shape[sh].y(), shape[sh].z());
        }
    }
    glEnd();

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

    gluLookAt(
        cam.pos.x(), cam.pos.y(), cam.pos.z(),
        cam.lookAt.x(), cam.lookAt.y(), cam.lookAt.z(),
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
            cam.lookAt = Point(cam.lookAt.x(), cam.lookAt.y(), cam.lookAt.z() - moveSpeed);
            break;
        case 's':
            cam.pos = Point(cam.pos.x(), cam.pos.y(), cam.pos.z() + moveSpeed);
            cam.lookAt = Point(cam.lookAt.x(), cam.lookAt.y(), cam.lookAt.z() + moveSpeed);
            break;
        case 'a':
            cam.pos = Point(cam.pos.x() - moveSpeed, cam.pos.y(), cam.pos.z());
            cam.lookAt = Point(cam.lookAt.x() - moveSpeed, cam.lookAt.y(), cam.lookAt.z());
            break;
        case 'd':
            cam.pos = Point(cam.pos.x() + moveSpeed, cam.pos.y(), cam.pos.z());
            cam.lookAt = Point(cam.lookAt.x() + moveSpeed, cam.lookAt.y(), cam.lookAt.z());
            break;
        case 'q':
            cam.pos = Point(cam.pos.x(), cam.pos.y() - moveSpeed, cam.pos.z());
            cam.lookAt = Point(cam.lookAt.x(), cam.lookAt.y() - moveSpeed, cam.lookAt.z());
            break;
        case 'e':
            cam.pos = Point(cam.pos.x(), cam.pos.y() + moveSpeed, cam.pos.z());
            cam.lookAt = Point(cam.lookAt.x(), cam.lookAt.y() + moveSpeed, cam.lookAt.z());
            break;
    }

    // Redraw the scene with the updated camera position
    glutPostRedisplay();
}


int main(int argc, char** argv) {
    readXML(argv[1]);

    // put GLUT’s init here
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(winDimensions.width, winDimensions.height);
    glutCreateWindow("CG");

    // put callback registry here
    glutReshapeFunc(changeSize);
    //glutIdleFunc(renderScene);
    glutDisplayFunc(renderScene);

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    // funcoes que queiram adicionar tipo rato ou teclas
	glutKeyboardFunc(keyboardFunc);

    // some OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    // enter GLUT’s main cycle
    glutMainLoop();

    return 1;
}
