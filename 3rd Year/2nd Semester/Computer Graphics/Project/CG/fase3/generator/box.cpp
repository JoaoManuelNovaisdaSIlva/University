#define _USE_MATH_DEFINES

#include "extras.hpp"
#include "forms.hpp"
#include <iostream>

using namespace std;

std::vector<Point> drawCube(double length, int div) {
	double mov = length / div;
	Point p1, p2, p3, p4;

	double hl = length / 2;

	std::vector<Point> v;

	for (double z = 0; z < length; z += mov) {
		for (double x = 0; x < length; x += mov) {

			p1 = Point(-hl+x, -hl, -hl+z);
			p2 = Point(-hl+x + mov, -hl, -hl+z);
			p3 = Point(-hl+x, -hl, -hl+z + mov);
			p4 = Point(-hl+x + mov, -hl, -hl+z + mov);

			v.push_back(p1);
			v.push_back(p2);
			v.push_back(p3);

			v.push_back(p4);
			v.push_back(p3);
			v.push_back(p2);

			p1 = Point(-hl, -hl+x, -hl+z);
			p2 = Point(-hl, -hl+x, -hl+z + mov);
			p3 = Point(-hl, -hl+x + mov, -hl+z);
			p4 = Point(-hl, -hl+x + mov, -hl+z + mov);

			v.push_back(p1);
			v.push_back(p2);
			v.push_back(p3);

			v.push_back(p4);
			v.push_back(p3);
			v.push_back(p2);

			p1 = Point(-hl+x, -hl+z, -hl);
			p2 = Point(-hl+x, -hl+z + mov, -hl);
			p3 = Point(-hl+x + mov, -hl+z, -hl);
			p4 = Point(-hl+x + mov, -hl+z + mov, -hl);

			v.push_back(p1);
			v.push_back(p2);
			v.push_back(p3);

			v.push_back(p4);
			v.push_back(p3);
			v.push_back(p2);

			p1 = Point(-hl+x, -hl+length, -hl+z);
			p2 = Point(-hl+x + mov, -hl+length, -hl+z);
			p3 = Point(-hl+x, -hl+length, -hl+z + mov);
			p4 = Point(-hl+x + mov, -hl+length, -hl+z + mov);

			v.push_back(p1);
			v.push_back(p3);
			v.push_back(p2);

			v.push_back(p4);
			v.push_back(p2);
			v.push_back(p3);

			p1 = Point(-hl+length, -hl+x, -hl+z);
			p2 = Point(-hl+length, -hl+x, -hl+z + mov);
			p3 = Point(-hl+length, -hl+x + mov, -hl+z);
			p4 = Point(-hl+length, -hl+x + mov, -hl+z + mov);

			v.push_back(p1);
			v.push_back(p3);
			v.push_back(p2);

			v.push_back(p4);
			v.push_back(p2);
			v.push_back(p3);

			p1 = Point(-hl+x, -hl+z, -hl+length);
			p2 = Point(-hl+x, -hl+z + mov, -hl+length);
			p3 = Point(-hl+x + mov, -hl+z, -hl+length);
			p4 = Point(-hl+x + mov, -hl+z + mov, -hl+length);

			v.push_back(p1);
			v.push_back(p3);
			v.push_back(p2);

			v.push_back(p4);
			v.push_back(p2);
			v.push_back(p3);
		}
	}

	return v;
}
