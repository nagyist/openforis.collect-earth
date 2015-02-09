package org.openforis.collect.earth.core.handlers;

import org.openforis.collect.model.NodeChangeSet;
import org.openforis.collect.model.RecordUpdater;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author Alfonso Sanchez-Paus Diaz
 *
 */
public abstract class AbstractAttributeHandler<C> {

	private String prefix;
	
	protected RecordUpdater recordUpdater;

	public AbstractAttributeHandler(String prefix) {
		super();
		this.prefix = prefix;
		this.recordUpdater = new RecordUpdater();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NodeChangeSet addOrUpdate(String parameterName, String parameterValue, Entity entity, int parameterChildIndex) {
		NodeChangeSet changeSet = null;
		String cleanParameterName = removePrefix(parameterName);
		Node<? extends NodeDefinition> node = entity.get(cleanParameterName, parameterChildIndex);

		if (parameterValue.trim().length() > 0) {
			if (node == null) {
				changeSet = addToEntity(parameterName, parameterValue, entity);
			} else if (node instanceof Attribute) {
				Attribute attribute = (Attribute) entity.get(cleanParameterName, parameterChildIndex);
				Value value = (Value) getAttributeValue(parameterValue);
				changeSet = recordUpdater.updateAttribute(attribute, value);
//				attribute.setValue(value);
			}
		}
		return changeSet;
	}

	protected abstract NodeChangeSet addToEntity(String parameterName, String parameterValue, Entity entity);

	public String getAttributeFromParameter(String parameterName, Entity entity) {
		return getAttributeFromParameter(parameterName, entity, 0);
	}

	public abstract String getAttributeFromParameter(String parameterName, Entity entity, int index);

	protected abstract C getAttributeValue(String parameterValue);

	public String getPrefix() {
		return prefix;
	}

	public boolean isParameterParseable(String parameterName) {
		return parameterName.startsWith(getPrefix());
	}

	public boolean isParseable(Node<?> value) {
		return isParseable(value.getDefinition());
	}

	public abstract boolean isParseable(NodeDefinition def);
	
	protected String removePrefix(String parameterName) {
		if (parameterName.startsWith(prefix)) {
			return parameterName.substring(prefix.length());
		} else {
			return parameterName;
		}

	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isMultiValueAware(){
		return false;
	}
}
