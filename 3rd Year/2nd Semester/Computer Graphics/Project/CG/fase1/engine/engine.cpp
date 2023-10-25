#include <stdlib.h>
#include <stdio.h>
#include <fstream>
#include <string>
#include <vector>

#ifdef _APPLE_
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

#include "tinyxml2.h"
#include "point.hpp"

using namespace std;
using namespace tinyxml2;

XMLDocument doc;
std::vector<std::vector<Point>> solids;

struct Polar {
    double radius;
    double alpha;
    double beta;
};

struct camPos {
    Point pos = Point(2, 2, 3);
    Point up = Point(0, 1, 0);
    double alpha = -M_PI_2, beta = -M_PI_2;
    float fov = 0, far = 0, near = 0;
};

struct Size {
    int width = 1020;
    int height = 800;
};

camPos camera;
Size windowSize;

void parsePoint(XMLElement* elemento, Point* ponto) {
    float x, y, z;

    if (elemento->Attribute("x"))
        x = stof(elemento->Attribute("x"));
    if (elemento->Attribute("y"))
        y = stof(elemento->Attribute("y"));
    if (elemento->Attribute("z"))
        z = stof(elemento->Attribute("z"));

    *ponto = Point(x, y, z);
}

Point camLookAtPoint() {
    return Point(
        (camera.pos.x() + (1 * cos(camera.beta) * sin(camera.alpha))),
        (camera.pos.y() + (1 * sin(camera.beta))),
        (camera.pos.z() + (1 * cos(camera.beta) * cos(camera.alpha)))
    );
}

void changeSize(int w, int h) {

    // Prevent a divide by zero, when window is too short
    // (you can’t make a window with zero width).
    if (h == 0)
        h = 1;
    // compute window's aspect ratio
    float ratio = w * 1.0f / h;
    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load the identity matrix
    glLoadIdentity();
    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);
    // Set the perspective
    gluPerspective(camera.fov, ratio, camera.near, camera.far);
    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void draw_axis() {
    glBegin(GL_LINES);
    //X axis in red
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-100.0f, 0.0f, 0.0f);
    glVertex3f(100.0f, 0.0f, 0.0f);
    //Y Axis in Green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -100.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);
    //Z Axis in Blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -100.0f);
    glVertex3f(0.0f, 0.0f, 100.0f);
    glEnd();
}



std::vector<Point> vectorize(const char* filename) {
    std::ifstream file(filename);
    if (!file.good()) {
        file.open(std::string("../").append(filename).c_str());
        if (!file.good()) {
            printf("Error opening file %s\n", filename);
            exit(1);
        }
    }

    std::string line;
    std::vector<Point> solid;

    printf("Reading %s\n", filename);
    std::getline(file, line);

    unsigned long N = std::stoul(line);
    solid.reserve(N);
    for (unsigned long i = 0; i < N; i++) {
        std::getline(file, line);
        double a, b, c;
        int matches = sscanf(line.c_str(), "%lf,%lf,%lf", &a, &b, &c);
        if (matches != 3) {
            printf("ERROR - invalid number of points in vertex %s\n", line.c_str());
            exit(1);
        }
        solid.push_back(Point(a, b, c));
    }

    file.close();
    printf("Finished reading %s\n", filename);
    return solid;
}


void renderScene() { //this function reads and draws the XML scene
    //buffer reset
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glLoadIdentity();

    Point look = camLookAtPoint();
    gluLookAt(camera.pos.x(), camera.pos.y(), camera.pos.z(),
        look.x(), look.y(), look.z(),
        camera.up.x(), camera.up.y(), camera.up.z());

    draw_axis();

    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    puts("Rendering...");
    for (unsigned long i = 0; i < solids.size(); i++) {
        std::vector<Point> solid = solids[i];
        glBegin(GL_TRIANGLES);
        for (unsigned long j = 0; j < solid.size(); j++) {
            glColor3ub(255, 255, 255); // set the color to white
            glVertex3d(solid[j].x(), solid[j].y(), solid[j].z());
        }
        glEnd();

    }
    puts("Render complete.");

    glutSwapBuffers();
}

void keyboardFunc(unsigned char key, int x, int y) {
    double aX = camera.pos.x(), aY = camera.pos.y(), aZ = camera.pos.z();
    switch (key) {
    case 'a':
        aX += 0.6 * cos(camera.alpha);
        aZ -= 0.6 * sin(camera.alpha);
        break;
    case 'd':
        aX -= 0.6 * cos(camera.alpha);
        aZ += 0.6 * sin(camera.alpha);
        break;
    case 's':
        aX -= 0.6 * sin(camera.alpha) * cos(camera.beta);
        aZ -= 0.6 * cos(camera.alpha) * cos(camera.beta);
        aY -= 0.6 * sin(camera.beta);
        break;
    case 'w':
        aX += 0.6 * sin(camera.alpha) * cos(camera.beta);
        aZ += 0.6 * cos(camera.alpha) * cos(camera.beta);
        aY += 0.6 * sin(camera.beta);
        break;
    case 'r':
        aY += 0.6;
        break;
    case 'f':
        aY -= 0.6;
        break;
    case '1':
        camera.alpha -= M_PI / 64;
        break;
    case '2':
        camera.alpha += M_PI / 64;
        break;
    case '3':
        camera.beta -= M_PI / 64;
        break;
    case '4':
        camera.beta += M_PI / 64;
        break;
    }

    camera.pos = Point(aX, aY, aZ);
    if (camera.beta > M_PI_2)
        camera.up = Point(0, -camera.up.y(), 0);

    glutPostRedisplay();
}

int main(int argc, char** argv) {
    if (argc != 2) {
        puts("São necessários mais argumentos.");
        return 1;
    }

    doc.LoadFile(argv[1]);
    if (doc.ErrorID()) {
        doc.LoadFile(std::string("../").append(argv[1]).c_str());
        if (doc.ErrorID()) {
            printf("%s\n", doc.ErrorStr());
            return doc.ErrorID();
        }
    }

    XMLElement* world = doc.FirstChildElement("world");
    if (world == NULL) {
        puts("<world> não foi encontrado.");
        return 1;
    }

    XMLElement* group = world->FirstChildElement("group");
    if (group != NULL) {
        XMLElement* models = group->FirstChildElement("models");
        if (models != NULL) {
            XMLElement* model = models->FirstChildElement();

            while (model) {
                if (!strcmp(model->Name(), "model")) {
                    solids.push_back(vectorize(model->Attribute("file")));
                }
                model = model->NextSiblingElement();
            }
        }

    }

    XMLElement* cam = world->FirstChildElement("camera");

    if (cam) {
        XMLElement* pos = cam->FirstChildElement("position");

        if (pos) {
            parsePoint(pos, &camera.pos);
        }

        XMLElement* lookAtXml = cam->FirstChildElement("lookAt");
        if (lookAtXml) {
            Point p;
            parsePoint(lookAtXml, &p);
            double x = p.x() - camera.pos.x();
            double y = p.y() - camera.pos.y();
            double z = p.z() - camera.pos.z();
            double r = sqrt(x * x + y * y + z * z);
            camera.alpha = M_PI + atan(x / z);
            camera.beta = asin(y / r);
            if (camera.beta > M_PI_2)
                camera.beta -= M_PI;
        }

        XMLElement* upXml = cam->FirstChildElement("up");
        if (upXml) {
            parsePoint(upXml, &camera.up);
        }

        XMLElement* projectionXml = cam->FirstChildElement("projection");
        if (projectionXml) {
            camera.fov = stof(projectionXml->Attribute("fov"));
            camera.far = stof(projectionXml->Attribute("far"));
            camera.near = stof(projectionXml->Attribute("near"));
        }
    }

    XMLElement* window = world->FirstChildElement("window");

    if (window) {
        windowSize.width = stoi(window->Attribute("width"));
        windowSize.height = stoi(window->Attribute("height"));
    }

    //init GLUT and the Window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(windowSize.width, windowSize.height);
    glutCreateWindow("CG");

    //Required callback registry
    glutDisplayFunc(renderScene);
    glutKeyboardFunc(keyboardFunc);
    glutReshapeFunc(changeSize);

    //OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

    //enter GLUT's main cycle
    glutMainLoop();

    return 0;
}
