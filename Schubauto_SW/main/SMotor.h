#pragma once
class SMotor
{

public:
	SMotor(int pwm, int dir, int brake, int sns);
	void Brake(bool br);
	void Go(int speed, bool forward);

	int CurrentConsumption();
private:
	int m_Pwm;
	int m_Dir;
	int m_Brake;
	int m_Sns;
};
