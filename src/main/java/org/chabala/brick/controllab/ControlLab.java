/*
 * Copyright © 2016 Greg Chabala
 *
 * This file is part of brick-control-lab.
 *
 * brick-control-lab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * brick-control-lab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with brick-control-lab.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.chabala.brick.controllab;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Object to represent interacting with the LEGO® control lab interface.
 */
public interface ControlLab extends Closeable {

    /**
     * Returns a new ControlLab instance.
     * @return a new ControlLab instance
     */
    static ControlLab newControlLab() {
        return new ControlLabImpl();
    }

    /**
     * List available serial ports on this machine. May change over time due to
     * hot pluggable USB serial port adapters and the like.
     *
     * @return a list of system specific string identifiers for serial ports
     */
    List<String> getAvailablePorts();

    /**
     * Opens a connection to the control lab on the specified port.
     * @param portName system specific serial port identifier
     * @throws IOException if any number of possible communication issues occurs
     */
    void open(String portName) throws IOException;

    /**
     * Stops sending power to the specified outputs.
     * @param outputs outputs to stop
     * @throws IOException if any number of possible communication issues occurs
     */
    void turnOutputOff(Set<Output> outputs) throws IOException;

    /**
     * Starts sending power to the specified outputs.
     * @param outputs outputs to start
     * @throws IOException if any number of possible communication issues occurs
     */
    void turnOutputOn(Set<Output> outputs) throws IOException;

    /**
     * Sets the {@link Direction} of the specified outputs. Direction may be changed
     * while the outputs are powered or unpowered.
     * @param direction desired direction
     * @param outputs which outputs to change
     * @throws IOException if any number of possible communication issues occurs
     */
    void setOutputDirection(Direction direction, Set<Output> outputs) throws IOException;

    /**
     * Attach a listener for {@link StopButtonEvent}s.
     *
     * <p>Multiple listeners are allowed. A listener instance will only be registered
     * once even if it is added multiple times.
     * @param listener listener to add
     */
    void addStopButtonListener(StopButtonListener listener);

    /**
     * Remove a listener for {@link StopButtonEvent}s.
     * @param listener listener to remove
     */
    void removeStopButtonListener(StopButtonListener listener);

    /**
     * Disconnects from the control lab and releases any resources.
     * @throws IOException if any number of possible communication issues occurs
     */
    @Override
    void close() throws IOException;
}
