package rlmixins.wrapper;

public interface IEntity {
	
	double rlmixins$getPrevMotionX();
	double rlmixins$getPrevMotionZ();
	
	int rlmixins$getLastBounceTick();
	void rlmixins$setLastBounceTick(int val);
}