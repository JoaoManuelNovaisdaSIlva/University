#include <vector>
#include "point.hpp"

std::vector<Point> drawPlane(double side, int div) {
    std::vector<Point> plane;
    double half = side / 2;

    Point p1 = Point(-half, 0, -half);
    Point p2 = p1.invertZ();
    Point p3 = p2.invertX();
    Point p4 = p1.invertX();

    double delta = side / div;

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

            plane.push_back(p1);
            plane.push_back(p2);
            plane.push_back(p4);

            plane.push_back(p2);
            plane.push_back(p3);
            plane.push_back(p4);
        }
    }

    return plane;
}

