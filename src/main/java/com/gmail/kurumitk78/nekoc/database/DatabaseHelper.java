/*
 * This file is a part of project QuickShop, the name is DatabaseHelper.java
 *  Copyright (C) PotatoCraft Studio and contributors
 *
 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.gmail.kurumitk78.nekoc.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import com.gmail.kurumitk78.nekoc.NekoC;

/**
 * A Util to execute all SQLs.
 */
public class DatabaseHelper {

	private final DatabaseManager manager;

	private final NekoC plugin;

	public DatabaseHelper(NekoC plugin, DatabaseManager manager) throws SQLException {
		this.plugin = plugin;
		this.manager = manager;
		
	}

	/** - Creates a table in the database for name and appends the SQL parameters specified in fields. */

	private void createTaxTable(String name, String fields) {
		String sqlString = "CREATE TABLE " + plugin.getDbPrefix()
				+ name + " (" + fields + ");";
		manager.runInstantTask(new DatabaseTask(sqlString));
	} 
	
	/** - Selects everything in a table by name of table. */
	
	public WarpedResultSet selectAll(String table) throws SQLException {
		DatabaseConnection databaseConnection = manager.getDatabase().getConnection();
		Statement st = databaseConnection.get().createStatement();
		String selectAllTaxes = "SELECT * FROM " + plugin.getDbPrefix() + table;
		ResultSet resultSet = st.executeQuery(selectAllTaxes);
		return new WarpedResultSet(st, resultSet, databaseConnection);
	}

	/** - Runs an SQL operation raw. You'll need to supply the exact SQL. */
	public void executeSQLWithoutReturn(String sqlString) {
		manager.addDelayTask(new DatabaseTask(sqlString, (ps) -> {}));
	}
	
	/** - Runs an SQL operation raw with a return. You'll need to supply the exact SQL. */
	
	public ResultSet executeSQLWithReturn(String sqlString) {
		ResultSet returnable = null;
		ResultSet resultSet = null;
		DatabaseConnection databaseConnection = manager.getDatabase().getConnection();
		Statement st = null;
		try {
			st = databaseConnection.get().createStatement();
		} catch (SQLException e) {
			// ahhh shit
			e.printStackTrace();
			databaseConnection.release();
			return null;
		}
		try {
			returnable = st.executeQuery(sqlString);
		} catch (SQLException e) {
			// ahhh shitttttttt
			e.printStackTrace();
			databaseConnection.release();
			return null;
		}

		databaseConnection.release();
		return returnable;
	}
}
