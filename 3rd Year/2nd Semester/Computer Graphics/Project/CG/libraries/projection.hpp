#ifndef Engine_Projection_H
#define Engine_Projection_H

#include <stdio.h>

using  namespace std;

class Projection{
        private:
        double fov;
        double near;
        double far;

        public:
        Projection();
        Projection (double fov, double near, double far);
        double getFov();
        double getNear();
        double getFar();
        void setFov(double a);
        void setNear(double a);
        void setFar(double a);
};

#endif
