package com.bug1312.javajson;

public interface IModelPartReloader {
	default public void registerJavaJSON() { ModelReloaderRegistry.register(this); };
	default public void reload() {};
}
