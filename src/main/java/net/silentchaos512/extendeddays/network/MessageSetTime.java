package net.silentchaos512.extendeddays.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.silentchaos512.extendeddays.event.TimeEvents;
import net.silentchaos512.lib.network.MessageSL;

public class MessageSetTime extends MessageSL {
    public long worldTime;
    public int extendedTime;

    public MessageSetTime() {
        this.worldTime = -1;
        this.extendedTime = -1;
    }

    public MessageSetTime(long worldTime, int extendedTime) {
        this.worldTime = worldTime;
        this.extendedTime = extendedTime;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        TimeEvents.INSTANCE.setTimeFromPacket(this);
        return null;
    }
}
