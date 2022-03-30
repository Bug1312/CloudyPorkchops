package com.bug1312.cloudyporkchops.client.event;

import com.bug1312.cloudyporkchops.client.init.CloudyEntityRenders;
import com.bug1312.cloudyporkchops.client.init.CloudyOverlays;
import com.bug1312.javajson.IReloadJavaJson;

public class JavaJsonReloader implements IReloadJavaJson {

	@Override
	public void reloadRenderers() {
		CloudyEntityRenders.init();
		CloudyOverlays.init(); 
	}

}
