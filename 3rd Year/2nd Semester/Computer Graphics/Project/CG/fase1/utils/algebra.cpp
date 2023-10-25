#include <cmath>

float getX(float r, float alpha, float beta) {
	return r * cos(beta) * sin(alpha);
}

float getY(float r, float beta) {
	return r * sin(beta);
}

float getZ(float r, float alpha, float beta) {
	return r * cos(beta) * cos(alpha);
}
