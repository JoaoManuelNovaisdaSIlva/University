#define _USE_MATH_DEFINES

#include "extras.hpp"
#include "forms.hpp"
#include <iostream>

using namespace std;

std::vector<Point> drawCone(double radius, double height, int slices, int stacks) {

	double slicedAlpha = (2 * M_PI) / slices;
	double stackHeight = height / stacks;
	Point p1, p2, p3, p4;

	std::vector<Point> v;

	p3 = Point(0.0f, 0.0f, 0.0f);
	for (int slice = 0; slice < slices; slice++) {
		p1 = Point(getX(radius, (slice + 1) * slicedAlpha, 0), 0.0f, getZ(radius, (slice + 1) * slicedAlpha, 0));
		p2 = Point(getX(radius, slice * slicedAlpha, 0), 0.0f, getZ(radius, slice * slicedAlpha, 0));

		v.push_back(p1);
		v.push_back(p2);
		v.push_back(p3);
	}


	for (int stack = 0; stack < stacks - 1; stack++) {
		for (int slice = 0; slice < slices; slice++) {

			p1 = Point(getX(radius - stack * radius / stacks, slice * slicedAlpha, 0), stackHeight * stack, getZ(radius - stack * radius / stacks, slice * slicedAlpha, 0));
			p2 = Point(getX(radius - stack * radius / stacks, (slice + 1) * slicedAlpha, 0), stackHeight * stack, getZ(radius - stack * radius / stacks, (slice + 1) * slicedAlpha, 0));
			p3 = Point(getX(radius - (stack + 1) * radius / stacks, (slice + 1) * slicedAlpha, 0), stackHeight * (stack + 1), getZ(radius - (stack + 1) * radius / stacks, (slice + 1) * slicedAlpha, 0));
			p4 = Point(getX(radius - (stack + 1) * radius / stacks, slice * slicedAlpha, 0), stackHeight * (stack + 1), getZ(radius - (stack + 1) * radius / stacks, slice * slicedAlpha, 0));

			v.push_back(p1);
			v.push_back(p2);
			v.push_back(p3);

			v.push_back(p1);
			v.push_back(p3);
			v.push_back(p4);
		}
	}


	p3 = Point(0.0f, height, 0.0f);
	for (int slice = 0; slice < slices; slice++) {
		p1 = Point(getX(radius - ((stacks - 1) * radius / stacks), slice * slicedAlpha, 0), height - height / stacks, getZ(radius - ((stacks - 1) * radius / stacks), slice * slicedAlpha, 0));
		p2 = Point(getX(radius - ((stacks - 1) * radius / stacks), (slice + 1) * slicedAlpha, 0), height - height / stacks, getZ(radius - ((stacks - 1) * radius / stacks), (slice + 1) * slicedAlpha, 0));

		v.push_back(p1);
		v.push_back(p2);
		v.push_back(p3);
	}

	return v;
}
