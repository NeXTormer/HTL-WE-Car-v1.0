#include "SMotor.h"

SMotor::SMotor(int pwm, int dir, int brake, int sns)
	: m_Pwm(pwm), m_Dir(dir), m_Brake(brake), m_Sns(sns)
{
	pinMode(m_Brake, OUTPUT);
	pinMode(m_Dir, OUTPUT);
}


void SMotor::Brake(bool en)
{
	digitalWrite(m_Brake, en);
}

void SMotor::Go(int speed, bool forward)
{
	digitalWrite(m_Brake, LOW);
	digitalWrite(m_Dir, forward);
	analogWrite(m_Pwm, speed);

}

int SMotor::CurrentConsumption()
{
	return analogRead(m_Sns);
}
