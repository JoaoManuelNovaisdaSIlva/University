#include "bezierPatch.hpp"
#include "extras.hpp"

#include <fstream>
#include <sstream>
#include <iostream>
#include <stdlib.h>
#include <stdio.h>

using namespace std;

Bezier::Bezier(string patch_file, int tesselation) {
    _tess = tesselation;

    int cPatch = 0, cPoints;

    ifstream fp("../patchFiles/" + patch_file);

    fp >> cPatch;
    _nPatches = cPatch;
    string value;

    for (; cPatch > 0; cPatch--) {
        vector<int> a;
        for (int i = 0; i < 16; i++) {
            fp >> value;
            a.push_back(stoi(value));
        }
        patches.push_back(a);
    }

    fp >> cPoints;
    _nCPoints = cPoints;
    string x, y, z;

    for (; cPoints > 0; cPoints--) {
        fp >> x >> y >> z;
        controlPoints.push_back(Point(stod(x), stod(y), stod(z)));
    }

    fp.close();
}

Point Bezier::getGlobalBezierPatchPoint(int patchNum, double u, double v) {
    vector<Point> patch;
    vector<int> patchPoints = patches[patchNum];
    vector<Point> uPoints;
    double pos[3];

    for (int i = 0; i < patchPoints.size(); i++)
        patch.push_back(controlPoints[patchPoints[i]]);

    for (int i = 0; i < patchPoints.size(); i+=4) {
        double p[3];
        getBezierPatchPoint(u, patch[i], patch[i + 1], patch[i + 2], patch[i + 3], p);
        uPoints.push_back(Point(p[0], p[1], p[2]));
    }

    getBezierPatchPoint(v, uPoints[0], uPoints[1], uPoints[2], uPoints[3], pos);

    return Point(pos[0], pos[1], pos[2]);
}

vector<Point> Bezier::generate() {
    vector<Point> points;
    double tess = 1.0 / _tess;

    for (int patch = 0; patch < _nPatches; patch++)
        for (double u = 0; u < 1; u += tess)
            for (double v = 0; v < 1; v += tess) {

                Point p0 = getGlobalBezierPatchPoint(patch, u, v);
                Point p1 = getGlobalBezierPatchPoint(patch, u, v + tess);
                Point p2 = getGlobalBezierPatchPoint(patch, u + tess, v);
                Point p3 = getGlobalBezierPatchPoint(patch, u + tess, v + tess);

                points.push_back(p3);
                points.push_back(p1);
                points.push_back(p0);

                points.push_back(p0);
                points.push_back(p2);
                points.push_back(p3);
            }

    return points;
}
