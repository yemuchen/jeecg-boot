<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: 0px 0px 20px 0px">

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
<!--          <a @click="handleEdit(record)">编辑</a>-->
          <a @click="handleDetail(record)">详情</a>
          <a-divider type="vertical"/>
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">-->
<!--              更多 <a-icon type="down"/>-->
<!--            </a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a href="javascript:;" @click="handleDetail(record)">详情</a>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <OrderProduct-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>
  import OrderProductModal from './modules/OrderProductModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'

  export default {
    name: "OrderProductList",
    mixins: [JeecgListMixin],
    components: {
      OrderProductModal,
    },
    data() {
      return {
        description: '信息',
        // 表头
        columns: [
          {
            title: '商品名',
            align:"center",
            dataIndex: 'title'
          },
          {
            title: '商品价格',
            align:"center",
            dataIndex: 'productPrice'
          },
          {
            title: '够买数量',
            align:"center",
            dataIndex: 'num'
          },
         {
          title: '操作',
          key: 'operation',
          align: "center",
          width: 130,
          scopedSlots: {customRender: 'action'},
        }],
        url: {
          list: "/mall/order/listOrderProductByMainId",
          delete: "/mall/order/deleteOrderProduct",
          deleteBatch: "/mall/order/deleteBatchOrderProduct",
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();
        getAction(this.url.list, {mainId: params.mainId}).then((res) => {
          if (res.success) {
            this.dataSource = res.result;
          } else {
            this.dataSource = null;
          }
        })
      },
      getMain(mainId) {
        this.queryParam.mainId = mainId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.mainId);
        this.$refs.modalForm.title = "添加订单商品";
      },
    }
  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>
