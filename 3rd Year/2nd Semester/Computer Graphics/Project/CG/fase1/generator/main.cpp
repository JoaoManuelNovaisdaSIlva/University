#include<iostream>
#include<fstream>
#include<vector>
#include<string>
#include<sstream>
#include"draw.hpp"

void writeInFile(std::vector<Point> pontos, std::string filename) {
	std::ofstream file (filename);

	file << pontos.size() << std::endl;

	for(Point ponto : pontos) {
		file << ponto.x() << ',' << ponto.y() << ',' << ponto.z() << std::endl;
	}

	file.close();
}

int main(int argc, char** argv) {
	auto figura = std::string(argv[1]);
	
	if(figura == "plane") {
		double lado = std::stod(argv[2]);
		int divi = std::stod(argv[3]);
		writeInFile(drawPlane(lado, divi), std::string(argv[4]));

	} 
	else if (figura == "box") {
		double comprimento = std::stod(argv[2]);
		double divi = std::stod(argv[3]);
		writeInFile(drawBox(comprimento, divi), std::string(argv[4]));
		
	}
	else if (figura == "sphere") {
		double radius = std::stod(argv[2]);
		int slices = std::stoi(argv[3]);
		int stacks = std::stoi(argv[4]);
		writeInFile(drawSphere(radius,slices,stacks), std::string(argv[5]));
	}
	else if (figura == "cone"){
		double raioB = std::stod(argv[2]);
		double altura = std::stod(argv[3]);
		int slices = std::stoi(argv[4]);
		int stacks = std::stoi(argv[5]);
		writeInFile(drawCone(raioB,altura,slices,stacks), std::string(argv[6]));
	}
}
