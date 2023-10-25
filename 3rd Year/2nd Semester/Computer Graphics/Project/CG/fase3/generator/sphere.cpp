#define _USE_MATH_DEFINES

#include "extras.hpp"
#include "forms.hpp"
#include <iostream>

using namespace std;

std::vector<Point> drawSphere(double radius, int slices, int stacks) {

	double alpha = 2 * M_PI / slices;
	double beta = M_PI / stacks;
	Point p1, p2, p3, p4;

	std::vector<Point> v;

	double stackA = -M_PI / 2, stackANext = stackA + beta;
	for (int stack = 0; stack < stacks; stack++) {

		p1 = Point(getX(radius, 0, stackANext), getY(radius, stackANext), getZ(radius, 0, stackANext));
		p3 = Point(getX(radius, 0, stackA), getY(radius, stackA), getZ(radius, 0, stackA));

		double sliceA = alpha;
		for (int slice = 0; slice < slices; slice++) {

			p2 = Point(getX(radius, sliceA, stackANext), getY(radius, stackANext), getZ(radius, sliceA, stackANext));
			p4 = Point(getX(radius, sliceA, stackA), getY(radius, stackA), getZ(radius, sliceA, stackA));

			if (stack != stacks - 1) {
				v.push_back(p1);
				v.push_back(p4);
				v.push_back(p2);
			}

			if (stack != 0) {
				v.push_back(p3);
				v.push_back(p4);
				v.push_back(p1);
			}

			p1 = p2; p3 = p4;
			sliceA += alpha;
		}
		stackA = stackANext;
		stackANext += beta;
	}

	return v;
}
