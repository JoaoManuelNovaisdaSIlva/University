#include <math.h>
#include "point.hpp"

double getX(double r, double alpha, double beta);
double getY(double r, double beta);
double getZ(double r, double alpha, double beta);

void buildRotMatrix(double* x, double* y, double* z, float* m);
void cross(double* a, double* b, double* res);
void normalize(double* a);
void multMatrixVector(double* m, double* v, double* res);
void getPoint(double t, Point p0, Point p1, Point p2, Point p3, double* pos, double* deriv, double m[4][4]);
void getCatmullRomPoint(double t, Point p0, Point p1, Point p2, Point p3, double* pos, double* deriv);
void getBezierPatchPoint(double t, Point p0, Point p1, Point p2, Point p3, double* pos);
