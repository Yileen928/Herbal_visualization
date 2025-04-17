//药材数据表格
<template>
  <div class="text-table" style="width: 100%; height: 390px">
    <table
      border="1"
      style="
        width: 100%;
        height: 100%;
        color: rgba(211, 182, 148, 0.9);
        border-collapse: collapse;
        border-color: #d3d3d3;
      "
    >
      <thead>
        <tr>
          <th>药名</th>
          <th>主要产地</th>
          <th>产量占比（全国）</th>
          <th>云药名称</th>
          <th>主要产地</th>
          <th>产量占比（全国）</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>三七</td>
          <td>文山州</td>
          <td>95%以上</td>
          <td>滇重楼</td>
          <td>普洱等</td>
          <td>90%以上</td>
        </tr>
        <tr>
          <td>灯盏花</td>
          <td>红河等</td>
          <td>100%（原料种植）</td>
          <td>铁皮石斛</td>
          <td>普洱等</td>
          <td>65%以上</td>
        </tr>
        <tr>
          <td>砂仁</td>
          <td>红河等</td>
          <td>全国第一</td>
          <td>天麻</td>
          <td>玉溪等</td>
          <td>25%以上</td>
        </tr>
        <tr>
          <td>云茯苓</td>
          <td>大理等</td>
          <td>60%以上</td>
          <td>云当归</td>
          <td>丽江等</td>
          <td>20%</td>
        </tr>
        <tr>
          <td>云木香</td>
          <td>大理等</td>
          <td>50%以上</td>
          <td>滇龙胆</td>
          <td>普洱等</td>
          <td>90%以上</td>
        </tr>
        <tr style="color: rgba(39, 38, 38, 0.63);fontSize:11px;">
          <td colspan="6" style="text-align: right;">
            数据来源：云南省人民政府
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
// No script needed for this static table
</script>