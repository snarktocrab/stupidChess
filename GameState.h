#ifndef _GAMESTATE_H
#define _GAMESTATE_H

#include <string>
#include <vector>

class GameState // Base class
{
public:
	virtual void update() = 0;
	virtual void render() = 0;

	virtual bool onEnter() = 0;
	virtual bool onExit() = 0;

	virtual std::string getStateID() const = 0;

protected:
	std::vector<std::string> m_textureIDList; // For future
};

#endif