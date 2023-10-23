#include "extras.hpp"

double getX(double r, double alpha, double beta) {
	return r * cos(beta) * sin(alpha);
}

double getY(double r, double beta) {
	return r * sin(beta);
}

double getZ(double r, double alpha, double beta) {
	return r * cos(beta) * cos(alpha);
}

void buildRotMatrix(double* x, double* y, double* z, float* m) {
    m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
	m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
	m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
	m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}

void cross(double* a, double* b, double* res) {
    res[0] = a[1] * b[2] - a[2] * b[1];
	res[1] = a[2] * b[0] - a[0] * b[2];
	res[2] = a[0] * b[1] - a[1] * b[0];
}

void normalize(double* a) {
    double l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0] / l;
    a[1] = a[1] / l;
    a[2] = a[2] / l;
}

void multMatrixVector(double* m, double* v, double* res) {
    for (int j = 0; j < 4; ++j) {
        res[j] = 0;
        for (int k = 0; k < 4; ++k) {
            res[j] += v[k] * m[j * 4 + k];
        }
    }
}

void getPoint(double t, Point p0, Point p1, Point p2, Point p3, double* pos, double* deriv, double m[4][4]) {
    double a[4];

    double p[3][4] = { {p0.x(), p1.x(), p2.x(), p3.x()},
                       {p0.y(), p1.y(), p2.y(), p3.y()}, 
                       {p0.z(), p1.z(), p2.z(), p3.z()} };

    for (int i = 0; i < 3; i++) {
        multMatrixVector(*m, p[i], a);

        pos[i] = t * t * t * a[0] + t * t * a[1] + t * a[2] + a[3];

        deriv[i] = 3 * t * t * a[0] + 2 * t * a[1] + a[2];
    }
}

void getCatmullRomPoint(double t, Point p0, Point p1, Point p2, Point p3, double* pos, double* deriv) {
    double m[4][4] = { {-0.5f,  1.5f, -1.5f,  0.5f},
                       { 1.0f, -2.5f,  2.0f, -0.5f},
                       {-0.5f,  0.0f,  0.5f,  0.0f},
                       { 0.0f,  1.0f,  0.0f,  0.0f} };

    getPoint(t, p0, p1, p2, p3, pos, deriv, m);
}

void getBezierPatchPoint(double t, Point p0, Point p1, Point p2, Point p3, double* pos) {

	double m[4][4] = { {-1.0f, +3.0f, -3.0f, +1.0f},
					   {+3.0f, -6.0f, +3.0f, +0.0f},
					   {-3.0f, +3.0f, +0.0f, +0.0f},
					   {+1.0f, +0.0f, +0.0f, +0.0f} };
	double d[3];
	getPoint(t, p0, p1, p2, p3, pos, d, m);
}
