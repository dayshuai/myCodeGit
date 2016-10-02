@Controller
@RequestMapping("/${table.mappingName?uncap_first}")
public class ${table.mappingName?cap_first}Controller extends BaseController{ 

	/** 日志管理 */
    private static final Logger logger = LoggerFactory.getLogger(${table.mappingName?cap_first}Controller.class);
	
	/** ${table.tableTitle}管理 */
    @Autowired
	private ${table.mappingName?cap_first}Service ${table.mappingName?uncap_first}Service;
	
    @RequestMapping()
    public String index(Model model) {
		return "${table.mappingName?uncap_first}";
    }
    
	/**
     * 保存${table.tableTitle}
     * 
     * @param model
     * @param ${table.mappingName?uncap_first}
     * @return
     */
	
    @ResponseBody
    @RequestMapping("insert${table.mappingName?cap_first}")
    public Map<String, Object> insert${table.mappingName?cap_first}(Model model, ${table.mappingName?cap_first} ${table.mappingName?uncap_first}) {
		try {
		    ${table.mappingName?uncap_first}Service.insert${table.mappingName?cap_first}(${table.mappingName?uncap_first});
		} catch (Exception e) {
		    logger.error("添加${table.tableTitle}失败,Message="+e.getMessage(), e);
		    return resultFalse(e.getMessage());
		}
		return resultTrue();
    }


	/**
     * 修改${table.tableTitle}
     * 
     * @param model
     * @param column
     * @return
     */

    @ResponseBody
    @RequestMapping("update${table.mappingName?cap_first}")
    public Map<String, Object> update${table.mappingName?cap_first}(Model model, ${table.mappingName?cap_first} column) {
		try {
		    ${table.mappingName?uncap_first}Service.update${table.mappingName?cap_first}(column);
		} catch (Exception e) {
		    logger.error("修改${table.tableTitle}失败,"+e.getMessage(), e);
		    return resultFalse(e.getMessage());
		}
		return resultTrue();
    }


	/**
     * 删除${table.tableTitle}
     * 
     * @param model
     * @param ${javaPrimary?uncap_first}
     * @return
     */

    @ResponseBody
    @RequestMapping("delete${table.mappingName?cap_first}")
    public Map<String, Object> delete${table.mappingName?cap_first}(Model model, Integer ${javaPrimary?uncap_first}) {
		try {
		    ${table.mappingName?uncap_first}Service.delete${table.mappingName?cap_first}(${javaPrimary?uncap_first});
		} catch (Exception e) {
		    logger.error("删除${table.tableTitle}失败,Message="+e.getMessage(), e);
		    return resultFalse(e.getMessage());
		}
		return resultTrue();
    }
	


	/**
     * 批量删除${table.tableTitle}
     * 
     * @param model
     * @param ids
     * @return
     */

    @ResponseBody
    @RequestMapping("delete${table.mappingName?cap_first}s")
    public Map<String, Object> delete${table.mappingName?cap_first}s(Model model, String ids) {
		try {
		    ${table.mappingName?uncap_first}Service.delete${table.mappingName?cap_first}s(ids);
		} catch (Exception e) {
		    logger.error("批量删除${table.tableTitle}失败,Message="+e.getMessage(), e);
		    return resultFalse(e.getMessage());
		}
		return resultTrue();
    }

	
	 /**
     * 分页查询${table.tableTitle}
     * 
     * @param model
     * @param column
     * @return
     */
  
    @ResponseBody
    @RequestMapping("query${table.mappingName?cap_first}PaginationList")
    public Pagination queryPaginationList(Model model, ${table.mappingName?cap_first} column) {
		try {
		    return ${table.mappingName?uncap_first}Service.query${table.mappingName?cap_first}PaginationList(column);
		} catch (Exception e) {
		    logger.error("分页查询${table.tableTitle}列表失败,Message="+e.getMessage(), e);
		    return buildPagination(e.getMessage());
		}
    }


	
	
	/**
     * 查询${table.tableTitle}下拉框列表
     * 
     * @param model
     * @return
     */

    @ResponseBody
    @RequestMapping("query${table.mappingName?cap_first}Select")
    public Map<String, Object> query${table.mappingName?cap_first}Select(Model model) {
		try {
		    Map<Object, Object> dataMap = ${table.mappingName?uncap_first}Service.query${table.mappingName?cap_first}Select();
		    List<String> selectList = buildSelectList(dataMap);
		    return resultTrue(selectList);
		} catch (Exception e) {
		    logger.error("查询${table.tableTitle}下拉框列表失败,Message="+e.getMessage(), e);
		    return resultFalse(e.getMessage());
		}
    }

}
