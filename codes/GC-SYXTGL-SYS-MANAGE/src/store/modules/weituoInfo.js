import Vue from 'vue'
// import { ONL_AUTH_FIELDS } from "@/store/mutation-types"
// import { getAction } from '@/api/manage'


const weituoInfo = {
  state: {
    //存储当前委托登记新增的数据，用于步骤之间的数据传输
    baseInfos: {},
    yangpinInfos: [],
    shiyanInfos: [],
    countYp: 1,
    countSy: 1,
  },
  // （大概）区别：mutations用来修改state数据 同步
  mutations: {
    SET_BASEINFO: (state, infos) => {
      // console.log('fields',fields)
      // Vue.set(state, 'baseInfos', fields)
      state.baseInfos = infos
    },
    SET_YANGPININFO: (state, infos) => {
      state.yangpinInfos = infos
    },
    SET_SHIYANINFO: (state, infos) => {
      state.shiyanInfos = infos
    },
    SET_COUNTYP: (state, infos) => {
      // console.log('fields',fields)
      // Vue.set(state, 'baseInfos', fields)
      state.countYp = infos
    },
    SET_COUNTSY: (state, infos) => {
      // console.log('fields',fields)
      // Vue.set(state, 'baseInfos', fields)
      state.countSy = infos
    },
  },
  // （大概）区别：actions用来设计复杂逻辑 异步
  actions: {
    setWeituoBaseInfos({ commit }, record) {
      commit('SET_BASEINFO', record)
    }
    // this.$store.state.weituoInfo.baseInfos
    // this.$store.dispatch('setWeituoBaseInfos', data)

  }
}

export default weituoInfo