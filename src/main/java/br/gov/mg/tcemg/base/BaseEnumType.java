package br.gov.mg.tcemg.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class BaseEnumType implements UserType, ParameterizedType {

	Class<?> forName;
	
	@Override
	public Object nullSafeGet(final ResultSet resultSet, final String[] names, final SessionImplementor session, final Object owner)
			throws HibernateException, SQLException {

		BaseEnum baseEnum = null;
		final String value = resultSet.getString(names[0]);
		if (!resultSet.wasNull() || (value != null && !value.isEmpty())) {
			
			try {
				baseEnum = (BaseEnum) forName.getMethod("getPorId", String.class).invoke(null, value);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
		}

		return baseEnum;
	}

	@Override
	public void nullSafeSet(final PreparedStatement statement, final Object value, final int index, final SessionImplementor session)
			throws HibernateException, SQLException {

		if (value == null) {
			statement.setNull(index, Types.VARCHAR);
		} else {
			final BaseEnum setVal = (BaseEnum) value;
			statement.setString(index, setVal.getId());
		}
	}

	@Override
	public Object assemble(final Serializable cached, final Object value) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(final Object value1, final Object value2) throws HibernateException {
		boolean result = false;
		if (value1 != null) {
			result = value1.equals(value2);
		} else if (value2 != null) {
			result = value2.equals(value1);
		} else {
			result = true;
		}
		return result;
	}

	@Override
	public int hashCode(final Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return forName;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@Override
	public void setParameterValues(Properties parameters) {

		try {
			Object classe = parameters.get("BaseEnum");
			forName = Class.forName(classe.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}