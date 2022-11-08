<script setup>
defineEmits(['close'])
defineProps({
    routes: {
      type: String
    },
    role: {
        type: String
    }
})
</script>
 
<template>

<div class="modal-mask">
      <div class="modal-wrapper">
          <div class="modal-container bg-[#855B52]">
              <div class="modal-header text-lg font-bold text-white"  >
                  <h3> Tutorial </h3>
              </div>
              <div class="modal-body text-white ">
                <span v-if="routes === 'home'">Event Tutorial <br/>
                - ปุ่ม booking จะเป็นการสร้าง event โดยมีข้อมูล <br/>
                 1.Category คือ เลือกวิชาที่ต้องการจะนัดหมาย <br/>
                 2.StartTime คือ เวลาที่ต้องการจะนัดหมาย <br/>
                 3.Duration คือ เวลาในการพูดคุยของแต่ละวิชา *ไม่สามารถแก้ไขได้ <br/>
                 4.Name คือ ชื่อของคนที่ต้องการจะนัดหมาย เช่น สมชาย ใจดี TT-2 <br/>
                 5.Email คือ email ของคนที่ต้องการจะนัดหมาย *ไม่สามารถแก้ไขได้  <br/>
                 6.Notes คือ สิ่งที่ต้องการจะบอกกับอาจารย์เพิ่มเติม <br/>
                - สามารถลบหรือแก้ไขได้โดยกดปุ่ม Delete และ Edit ตามลำดับ  <br/>
                - สามารถกดดูรายละเอียดได้โดยกด More Details <br/>
                - ด้านล่างคือ event-list ที่จะเห็นการนัดหมายตามแต่ละ Role 
                </span>
                <span v-else-if="routes === 'eventcategory' & (role === 'LECTURER' || role === 'ADMIN')">Event Category Tutorial <br/>
                - ในหน้า Event Categories มีข้อมูลดังนี้คือ <br/>
                 1. Category Name คือ วิชาที่ต้องการจะนัดหมาย <br/>
                 2. Category Description คือ เป็นคำอธิบายของแต่ละวิชา <br/>
                 3. Duration คือ ระยะเวลาในการพูดคุยของแต่ละวิชา <br/>
                - และสามารถแก้ไขข้อมูลได้โดยกดปุ่ม edit
                 </span>
                <span v-else-if="routes === 'user' & role === 'ADMIN'   ">User Tutorial <br/>
                - ในหน้า User มีข้อมูล <br/>
                 1. Name คือ ชื่อของ User คนนั้น <br/>
                 2. Email คือ Email ของ User  <br/>
                 3. Role คือ ตำแหน่งของ User<br/>
                 - และสามารถกด Details เพื่อดูข้อมูลเพิ่มเติมได้ คือ สร้างและแก้ไข user คนนี้เมื่อใด <br/>
                 - สามารถลบหรือแก้ไขได้โดยกดปุ่ม Delete และ Edit ตามลำดับ
                </span>
                <span v-else-if="routes === 'adduser' & role === 'ADMIN'">Add User Tutorial <br/>
                - ในหน้า Add User จะสามารถสร้าง User ได้โดยมีข้อมูลที่ต้องระบุดังนี้ <br/>
                    1. Name คือชื่อของ User ที่ต้องการสร้าง <br/>
                    2. Email คือ Email ของ User ที่ต้องการสร้าง <br/>
                    3. Password คือช่องที่ระบุ Password ของ User ที่ต้องการสร้าง โดยจะต้องอยู่ระหว่าง 8-14 ตัวอักษร <br/>
                    4. Retype Password คือช่องที่ต้องใส่ Password ของ User ที่ต้องการสร้างอีกครั้ง <br/>
                    5. Dropdown มีให้เลือก 3 Role ดังนี้ 1.STUDENT 2.ADMIN 3.LECTURER โดยเเต่ละ Role จะมีสิทธิในการเข้าถึงที่เเตกต่างกัน <br/>
                - เมื่อกรอกข้อมูลครบทุกช่องเเล้วให้กด Create User เพื่อทำการสร้าง User ใหม่ขึ้นมา <br/>
                </span>
                <span v-else-if="routes === 'about_us'" class="text-2xl">There are no tutorial in this page ^w^</span>
          </div>
              <div class=" text-black flex justify-end">
                <button @click="$emit('close' , false)" class=" ml-3 mb-4  border border-solid bg-white  border-yellow-800 hover:bg-yellow-800 hover:text-black font-bold uppercase text-sm py-3 rounded outline-none focus:outline-none ease-linear transition-all duration-150 active show px-3">
                  Close
                </button>
              </div>
          </div>
      </div>
  </div>

</template>
 
<style>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: table;
    transition: opacity 0.3s ease;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-container {
    width: 1000px;
    margin: 0px auto;
    padding: 20px 30px;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
}

.modal-body {
    margin: 20px 0;
}

</style>