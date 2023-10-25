#include <vector>
#include "point.hpp"

std::vector<Point> drawBox(double length, int div) {

	double mov = length / div;
    Point p1, p2, p3, p4;
    std::vector<Point> v;
    double offset = length / 2.0;

    // draw the top face
    for (double z = -offset; z < offset - 0.0001; z += mov) {
        for (double x = -offset; x < offset - 0.0001; x += mov) {
            p1 = Point(x, offset, z);
            p2 = Point(x + mov, offset, z);
            p3 = Point(x, offset, z + mov);
            p4 = Point(x + mov, offset, z + mov);

            v.push_back(p1);
            v.push_back(p3);
            v.push_back(p2);

            v.push_back(p4);
            v.push_back(p2);
            v.push_back(p3);
        }
    }

    // draw the bottom face
    for (double z = -offset; z < offset - 0.0001; z += mov) {
        for (double x = -offset; x < offset - 0.0001; x += mov) {
            p1 = Point(x, -offset, z);
            p2 = Point(x + mov, -offset, z);
            p3 = Point(x, -offset, z + mov);
            p4 = Point(x + mov, -offset, z + mov);

            v.push_back(p1);
            v.push_back(p2);
            v.push_back(p3);

            v.push_back(p4);
            v.push_back(p3);
            v.push_back(p2);
        }
    }

    // draw the front face
    for (double y = -offset; y < offset - 0.0001; y += mov) {
        for (double x = -offset; x < offset - 0.0001; x += mov) {
            p1 = Point(x, y, offset);
            p2 = Point(x + mov, y, offset);
            p3 = Point(x, y + mov, offset);
            p4 = Point(x + mov, y + mov, offset);

            v.push_back(p1);
            v.push_back(p2);
            v.push_back(p3);

            v.push_back(p4);
            v.push_back(p3);
            v.push_back(p2);
        }
    }

    // draw the back face
    for (double y = -offset; y < offset - 0.0001; y += mov) {
        for (double x = -offset; x < offset - 0.0001; x += mov) {
            p1 = Point(x, y, -offset);
            p2 = Point(x + mov, y, -offset);
            p3 = Point(x, y + mov, -offset);
            p4 = Point(x + mov, y + mov, -offset);

            v.push_back(p1);
            v.push_back(p3);
            v.push_back(p2);

            v.push_back(p4);
            v.push_back(p2);
            v.push_back(p3);
        }
    }

	// draw the left face
	for (double y = -offset; y < offset - 0.0001; y += mov) {
    	for (double z = -offset; z < offset - 0.0001; z += mov) {
        	p1 = Point(-offset, y, z);
        	p2 = Point(-offset, y + mov, z);
        	p3 = Point(-offset, y, z + mov);
        	p4 = Point(-offset, y + mov, z + mov);

        	v.push_back(p1);
        	v.push_back(p3);
        	v.push_back(p2);

        	v.push_back(p4);
        	v.push_back(p2);
        	v.push_back(p3);
    	}
	}

	// draw the right face
	for (double x = -offset; x < offset - 0.0001; x += mov) {
    	for (double z = -offset; z < offset - 0.0001; z += mov) {
        	p1 = Point(offset, x, z);
        	p2 = Point(offset, x, z + mov);
        	p3 = Point(offset, x + mov, z + mov);
        	p4 = Point(offset, x + mov, z);

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
