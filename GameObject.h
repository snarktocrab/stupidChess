#ifndef _GAMEOBJECT_H
#define _GAMEOBJECT_H

#include <SDL.h>
#include "LoaderParams.h"
#include "InputHandler.h"

class GameObject // Base class
{
public:
	virtual void draw() = 0;
	virtual void update() = 0;
	virtual void clean() = 0;

protected:
	GameObject() {}
	virtual ~GameObject() {}
};

#endif