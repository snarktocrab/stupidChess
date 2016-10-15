#ifndef _GAMESTATEMACHINE_H
#define _GAMESTATEMACHINE_H

#include <vector>

#include "GameState.h"

class GameStateMachine
{
public:
	void pushState(GameState* pState);
	void changeState(GameState* pState);
	void popState();

	void update();
	void render();

	~GameStateMachine();

private:
	std::vector<GameState*> m_gameStates;
};

#endif