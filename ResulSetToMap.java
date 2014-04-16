public ResulSetToMap{
  String nomeTabella = "test";
  public static void main(String args[]) {
		new CrudToDb().select();
	}

	public void select() {
		try (Connection connection = StaticPool.getConnection()) {
			String sql = "SELECT * FROM "+nomeTabella;
			try (PreparedStatement table = connection.prepareStatement(sql)) {
				try (ResultSet executeQuery = table.executeQuery()) {
					long time = System.currentTimeMillis();
					resultSetToArrayList2(executeQuery);
					time = System.currentTimeMillis() - time;
					System.out.println("time 2: " + time);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try (ResultSet executeQuery = table.executeQuery()) {
					long time = System.currentTimeMillis();
					convertResultSetToList1(executeQuery);
					time = System.currentTimeMillis() - time;
					System.out.println("time 1: " + time);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String, List<Object>> resultSetToArrayList2(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		Map<String, List<Object>> map = new HashMap<>(columns);
		for (int i = 1; i <= columns; ++i) {
			map.put(md.getColumnName(i), new ArrayList<>());
		}
		while (rs.next()) {
			for (int i = 1; i <= columns; ++i) {
				map.get(md.getColumnName(i)).add(rs.getObject(i));
			}
		}

		return map;
	}

	private List<HashMap<String, Object>> convertResultSetToList1(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}

		return list;
	}
}
