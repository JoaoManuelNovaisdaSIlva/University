#include <string>
#include <cstring>
#include <vector>
#include <cstdlib>
#include <iostream>
#include <fstream>

#include "point.hpp"
#include "forms.hpp"
#include "bezierPatch.hpp"

using namespace std;

void writeFile(vector<Point> pontos, string filename) {
    ofstream file(filename);

    file << pontos.size() << endl;

    for (Point ponto : pontos) {
        file << ponto.x() << ';' << ponto.y() << ';' << ponto.z() << endl;
    }

    file.close();
}

int main(int argc, char** argv) {

    if (argc < 5) {
    	printf("Not enough arguments.\n");
	} 

    else {
        vector<Point> v;

        if (strcmp("sphere", argv[1]) == 0) {
            v = drawSphere(stod(argv[2]), stoi(argv[3]), stoi(argv[4]));
        }
        else if (strcmp("box", argv[1]) == 0) {
            v = drawCube(stod(argv[2]), stoi(argv[3]));
        }
        else if (strcmp("cone", argv[1]) == 0) {
            v = drawCone(stod(argv[2]), stod(argv[3]), stoi(argv[4]), stoi(argv[5]));
        }
        else if (strcmp("plane", argv[1]) == 0) {
            v = drawPlane(stod(argv[2]), stoi(argv[3]));
        }
		else if (strcmp("torus", argv[1]) == 0) {
			v = drawTorus(stod(argv[2]), stod(argv[3]), stoi(argv[4]), stoi(argv[5]));
		}
        else if (strcmp("patch", argv[1]) == 0) {
            Bezier b = Bezier(argv[2], stoi(argv[3]));
            v = b.generate();
        }
        else {
			printf("Format error.\n");
            exit(1);
        }

        writeFile(v, argv[argc-1]);
		printf("File: %s ready\n", argv[argc - 1]);
    }
}
