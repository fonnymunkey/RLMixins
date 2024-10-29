package rlmixins.wrapper;

import java.util.concurrent.locks.Lock;

public interface IObjectSpawnerMixin {
	
	Lock rlmixins$getLock();
}