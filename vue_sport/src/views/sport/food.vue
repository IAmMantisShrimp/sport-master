<template>
    <div>
        <el-tabs type="border-card" v-model="active" @tab-click="handleClick">
            <el-tab-pane label="食物分类" name="0">
                <food-type :active="active"/>
            </el-tab-pane>
            <el-tab-pane label="食物明细" name="1">
                <food :active="active"/>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import FoodType from "./food/FoodType";
import Food from "./food/Food";
export default {
    components: {
        FoodType,
        Food
    },
    data() {
        return {
            active: 0
        }
    },
    methods: {
        handleClick(tab, event){
            //this.active = tab.index;
            console.log(this.active);
        }
    }
}
</script>

<style lang="less" scoped>

</style>