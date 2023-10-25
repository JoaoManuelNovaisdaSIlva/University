#define _USE_MATH_DEFINES

#include "extras.hpp"
#include "forms.hpp"
#include <iostream>

using namespace std;

std::vector<Point> drawPlane(double length, int div) {
    std::vector<Point> v;
    double half = length / 2;

    Point p1 = Point(-half, 0, -half);
	Point p2 = Point(-half, 0, half);
	Point p3 = Point(half, 0, half);
	Point p4 = Point(half, 0, -half);

    double delta = length / div;

    for (int i = 0; i < div; i++) {
        for (int j = 0; j < div; j++) {
            double x1 = -half + i * delta;
            double x2 = x1 + delta;
            double z1 = -half + j * delta;
            double z2 = z1 + delta;

            Point p1(x1, 0, z1);
            Point p2(x1, 0, z2);
            Point p3(x2, 0, z2);
            Point p4(x2, 0, z1);

            v.push_back(p1);
            v.push_back(p2);
            v.push_back(p4);

            v.push_back(p2);
            v.push_back(p3);
            v.push_back(p4);
        }
    }

    return v;
}
