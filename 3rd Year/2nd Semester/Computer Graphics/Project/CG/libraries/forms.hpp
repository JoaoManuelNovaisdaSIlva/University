#include <vector>

#include "point.hpp"

std::vector<Point> drawPlane(double length, int div);
std::vector<Point> drawSphere(double radius, int slices, int stacks);
std::vector<Point> drawCube(double length, int div);
std::vector<Point> drawCone(double radius, double height, int slices, int stacks);
std::vector<Point> drawTorus(double dist, double radius, int slices, int stacks);
