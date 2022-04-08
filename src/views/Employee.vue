<template>
  <div >
    <div style="padding: 20px;">
      员工汇总
    </div>
    <div style="">
      <el-card>
        <div style="display: flex;
        justify-content: space-around;
        padding-right: 80px;
        ">
          <el-button v-if="checkPermission(['EMP_DELETE'])"  @click="delEmpBatch" type="primary"><i class="el-icon-delete-solid"></i> 批量删除</el-button>
          <el-button v-if="checkPermission(['EMP_ADD'])" style="margin-left: 0" @click="addEmp" type="primary"><i class="el-icon-plus"></i> 新增</el-button>
          <el-input v-model="query.name" style="width: 120px" placeholder="员工姓名"></el-input>
          <el-input v-model="query.no"  style="width: 120px" placeholder="员工编号"></el-input>
          <div>
            <el-select style="width: 120px" v-model="query.deptId" filterable placeholder="所在部门">
              <el-option
                  v-for="item in deptTypes"
                  :key="item.deptId"
                  :label="item.deptName"
                  :value="item.deptId">
              </el-option>
            </el-select>
          </div>
          <el-button @click="search" type="primary"><i class="el-icon-search"></i> 搜索</el-button>
          <el-button @click="reset" style="margin-left: 0px" type="danger"><i class="el-icon-refresh"></i> 重置</el-button>
        </div>


<!--        表格-->
        <div style="margin-top: 20px; width: 100%">
          <el-table
              border
              stripe
              :header-cell-style="{background:'#cadffa', color: '#887e7e'}"
              ref="multipleTable"
              :data="tableData"
              style="width: 100%;"
              @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="empNo" label="编号"></el-table-column>
            <el-table-column prop="empName" label="姓名"></el-table-column>
            <el-table-column prop="empGender" label="性别"></el-table-column>
            <el-table-column prop="empEmail" label="邮箱"></el-table-column>
            <el-table-column prop="deptName" label="部门"></el-table-column>
            <el-table-column prop="empPhone" label="联系电话"></el-table-column>
            <el-table-column prop="empType" label="员工类型"></el-table-column>
            <el-table-column prop="empIdcard" label="身份证号"></el-table-column>
            <el-table-column prop="empSalerycard" label="工资卡号"></el-table-column>
            <el-table-column label="操作" width="200px" align="center">
              <template slot-scope="scope">
                <el-button v-if="checkPermission(['EMP_EDIT'])" type="primary" @click="handleEdit(scope.row)" icon="el-icon-edit" circle></el-button>
                <el-popconfirm
                    v-if="checkPermission(['EMP_DELETE'])"
                    title="确定删除吗？"
                    @confirm="delEmp(scope.row)"
                    style="margin-left: 10px"
                >
                  <el-button slot="reference" type="danger" icon="el-icon-delete" circle></el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </div>

<!--        分页-->
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="query.page"
              :page-sizes="[2, 5, 10, 20]"
              :page-size="query.size"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pageTotal">
          </el-pagination>
        </div>
      </el-card>
    </div>



<!--    添加员工/编辑员工的对话框-->
    <div>
      <el-dialog title="员工信息" :visible.sync="dialogFormVisible">
        <el-form label-width="70px" ref="form" :model="form">
          <el-form-item prop="empNo" label="员工编号" >
            <el-input v-model="form.empNo" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="empName" label="员工姓名" >
            <el-input v-model="form.empName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="empIdcard" label="身份证号" >
            <el-input v-model="form.empIdcard" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="empSalerycard" label="工资卡号" >
            <el-input v-model="form.empSalerycard" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item  prop="empPassword" label="员工密码" >
            <el-input v-model="form.empPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item  prop="empGender" label="员工性别" >
            <el-select v-model="form.empGender" placeholder="请选择">
              <el-option
                  v-for="item in sexTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="empStatus" label="员工类型" >
            <el-select v-model="form.empStatus" placeholder="请选择员工类型" style="width:100%;">
              <el-option
                  v-for="item in empStatusTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="empEmail" label="员工邮箱" >
            <el-input v-model="form.empEmail" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="所在部门" >
            <el-select v-model="form.deptId" placeholder="请选择所在部门" style="width:100%;">
              <el-option
                  v-for="item in deptTypesEdit"
                  :key="item.deptId"
                  :label="item.deptName"
                  :value="item.deptId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="empType" label="员工角色" >
            <el-select v-model="form.empType" placeholder="请选择">
              <el-option
                  v-for="item in empTypes"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>


  </div>
</template>

<script>
import { checkPermission } from "@/utils/permission";
import { getAll as getRoleAll } from "@/api/role";
import {getAll,remove,removeEmpBatch as  reqRemoveEmpBatch, add as reqSaveEmp, update as reqUpdateEmp} from '@/api/employee';
import {getAll as getDeptAll} from '@/api/department';

export default {
  name: "DeptEmployee",
  data() {
    return {
      tableData: [],
      query: {
        name: "",
        no:"",
        page:1,
        size:5,
        deptId:""
      },
      sexTypes: [
        { label: "男", value: "男" },
        { label: "女", value: "女" }
      ],
      pageTotal:0,
      deptTypes:[],
      deptTypesEdit:[],
      dialogFormVisible: false,
      form: {},
      empTypes:[],
      empStatusTypes: [
        { label: "正式工", value: 1 },
        { label: "临时工", value: 0 }
      ],
      isAdd: false,
      multipleSelection: []
    }
  },
  created() {
    this.getData();
    let that = this;
    getDeptAll("").then(data=>{
      that.deptTypes = data;
      that.deptTypesEdit = JSON.parse(JSON.stringify(data))
      that.deptTypes.push({
        deptId:"",
        deptName:"所有部门"
      });
    })

    getRoleAll().then(data => {
      that.empTypes = data;
    });
  },
  methods: {
    search(){
      this.query.page = 1;
      this.query.size = 5;
      this.getData();
    },
    checkPermission,
    handleSizeChange(val) {
      this.query.size = val;
      console.log(`每页 ${val} 条`);
      this.getData();
    },
    handleCurrentChange(val) {
      this.query.page = val;
      console.log(`当前页: ${val}`);
      this.getData();
    },
    delEmpBatch() {
      let ids = this.multipleSelection.map(item => item.empId)
      console.log(ids);
      let vm = this;
      reqRemoveEmpBatch(ids).then(res => {
        if (res) {
          vm.$message({
            type: "success",
            message: "批量删除成功"
          })
          vm.reset()
        } else {
          vm.$message({
            type: "error",
            message: "批量删除失败"
          })
        }
        console.log(res);
      })
    },
    delEmp(row) {
      let vm = this;
      remove(row.empId).then(res => {
        if (res) {
          vm.$message({
            type: "success",
            message: "批量删除成功"
          })
          vm.reset()
        } else {
          vm.$message({
            type: "error",
            message: "批量删除失败"
          })
        }
      });
    },
    addEmp(){
      this.form = {}
      this.dialogFormVisible = true;
      this.isAdd = true;
    },
    save(){
      let vm = this;
      if (this.isAdd){
        reqSaveEmp(this.form).then(data => {
          console.log(data);
          if (data){
            this.dialogFormVisible = false;
            vm.reset()
            vm.$message({
              message: '新增员工成功',
              type: "success"
            })
          } else {
            vm.$message({
              message: '新增员工失败',
              type: "error"
            })
          }
        })
      } else {
        reqUpdateEmp(this.form).then(data => {
          console.log(data);
          if (data){
            this.dialogFormVisible = false;
            vm.reset()
            vm.$message({
              message: '新增员工成功',
              type: "success"
            })
          } else {
            vm.$message({
              message: '新增员工失败',
              type: "error"
            })
          }
        })
      }
    },
    getData() {
      getAll(this.query).then(res => {
        this.tableData = res.list;
        this.pageTotal = res.total;
      });
    },
    reset(){
      this.query = {
        name: "",
        no:"",
        page:1,
        size:5,
        deptId:""
      }
      this.getData();
    },
    handleEdit(row){
      this.isAdd = false;
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true;
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    }
  }
}
</script>

<style scoped>

</style>
