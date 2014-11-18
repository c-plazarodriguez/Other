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
package de.bund.bfr.knime.flink.jm;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "FlinkJobManagerConnection" Node.
 * Connects to the Flink job manager.
 * 
 * @author Arvid Heise
 */
public class FlinkJobManagerConnectionNodeFactory
		extends NodeFactory<FlinkJobManagerConnectionNodeModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeDialogPane createNodeDialogPane() {
		return new FlinkJobManagerConnectionNodeDialog();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FlinkJobManagerConnectionNodeModel createNodeModel() {
		return new FlinkJobManagerConnectionNodeModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeView<FlinkJobManagerConnectionNodeModel> createNodeView(final int viewIndex,
			final FlinkJobManagerConnectionNodeModel nodeModel) {
		return new FlinkJobManagerConnectionNodeView(nodeModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNrNodeViews() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasDialog() {
		return true;
	}

}
