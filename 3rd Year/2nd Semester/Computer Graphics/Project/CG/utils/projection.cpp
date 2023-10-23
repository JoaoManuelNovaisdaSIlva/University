#include "projection.hpp"

Projection::Projection(double fov, double near, double far){
  this->fov = fov;
  this->near = near;
  this->far = far;
}

Projection::Projection(){
  this->fov = 0;
  this->near = 0;
  this->far = 0;
}

double Projection::getFov(){
  return this->fov;
}

double Projection::getNear(){
  return this->near;
}

double Projection::getFar(){
  return this->far;
}

void Projection::setFov(double fov){
  this->fov = fov;
}

void Projection::setNear(double near){
  this->near = near;
}

void Projection::setFar(double far){
  this->far = far;
}
