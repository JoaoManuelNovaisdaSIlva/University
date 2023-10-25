#include <vector>
#include <string>

#include "point.hpp"

using namespace std;

class Bezier {
    private:
        vector<Point> controlPoints;
        vector<vector<int>> patches;
        int _tess;
        int _nCPoints;
        int _nPatches;

        Point getGlobalBezierPatchPoint(int patchNum, double u, double v);

    public:
        Bezier(string patch_file, int tess);
        vector<Point> generate();
};
