#define _USE_MATH_DEFINES

#include "extras.hpp"
#include "forms.hpp"
#include <iostream>

std::vector<Point> drawTorus(double innerRadius, double outerRadius, int slices, int stacks) {
   std::vector<Point> v;

    double averageRadius = (innerRadius + outerRadius) / 2;
    double radialDistance = averageRadius - innerRadius;

    double sliceAngleIncrement = (2 * M_PI) / slices;
    double stackAngleIncrement = (2 * M_PI) / stacks;

	Point p1, p2, p3, p4;

    for (int slice = 0; slice < slices; ++slice) {
        double sliceAngle = slice * sliceAngleIncrement;
        double nextSliceAngle = (slice + 1) * sliceAngleIncrement;

        for (int stack = 0; stack < stacks; ++stack) {
            double stackAngle = stack * stackAngleIncrement;
            double nextStackAngle = (stack + 1) * stackAngleIncrement;

            p1 = Point((averageRadius + radialDistance * cos(sliceAngle)) * cos(stackAngle), radialDistance * sin(sliceAngle), (averageRadius + radialDistance * cos(sliceAngle)) * sin(stackAngle));
            p2 = Point((averageRadius + radialDistance * cos(nextSliceAngle)) * cos(stackAngle), radialDistance * sin(nextSliceAngle), (averageRadius + radialDistance * cos(nextSliceAngle)) * sin(stackAngle));
            p3 = Point((averageRadius + radialDistance * cos(nextSliceAngle)) * cos(nextStackAngle), radialDistance * sin(nextSliceAngle), (averageRadius + radialDistance * cos(nextSliceAngle)) * sin(nextStackAngle));
            p4 = Point((averageRadius + radialDistance * cos(sliceAngle)) * cos(nextStackAngle), radialDistance * sin(sliceAngle), (averageRadius + radialDistance * cos(sliceAngle)) * sin(nextStackAngle));

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
