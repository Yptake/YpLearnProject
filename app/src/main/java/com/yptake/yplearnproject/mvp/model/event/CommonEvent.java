package com.yptake.yplearnproject.mvp.model.event;

import com.yptake.yplearnproject.utils.network.NetworkType;

public class CommonEvent {


    public static class NetChangerEvent {

        public NetworkType type;

        public NetChangerEvent(NetworkType type) {
            this.type = type;
        }
    }







}
