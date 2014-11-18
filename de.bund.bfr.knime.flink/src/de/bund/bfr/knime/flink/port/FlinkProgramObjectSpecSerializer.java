/*******************************************************************************
 * Copyright (c) 2014 Federal Institute for Risk Assessment (BfR), Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.bund.bfr.knime.flink.port;

import java.io.IOException;

import org.knime.core.node.port.PortObjectSpec.PortObjectSpecSerializer;
import org.knime.core.node.port.PortObjectSpecZipInputStream;
import org.knime.core.node.port.PortObjectSpecZipOutputStream;

import de.bund.bfr.knime.flink.FlinkProgramWithUsage;
import de.bund.bfr.knime.flink.SerializationHelper;

/**
 * 
 */
public class FlinkProgramObjectSpecSerializer extends
		PortObjectSpecSerializer<FlinkProgramObjectSpec> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.knime.core.node.port.PortObjectSpec.PortObjectSpecSerializer#loadPortObjectSpec(org.knime.core.node.port.
	 * PortObjectSpecZipInputStream)
	 */
	@Override
	public FlinkProgramObjectSpec loadPortObjectSpec(PortObjectSpecZipInputStream in) throws IOException {
		FlinkProgramObjectSpec spec = new FlinkProgramObjectSpec();
		spec.setProgram(SerializationHelper.<FlinkProgramWithUsage> readObject(in));
		return spec;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.knime.core.node.port.PortObjectSpec.PortObjectSpecSerializer#savePortObjectSpec(org.knime.core.node.port.
	 * PortObjectSpec, org.knime.core.node.port.PortObjectSpecZipOutputStream)
	 */
	@Override
	public void savePortObjectSpec(FlinkProgramObjectSpec portObjectSpec, PortObjectSpecZipOutputStream out)
			throws IOException {
		SerializationHelper.writeObject(out, portObjectSpec.getProgram());
	}

}
