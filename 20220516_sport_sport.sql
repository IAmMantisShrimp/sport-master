INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (1, '跆拳道', '5-60', null, null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (2, '田径', '6-80岁', '大腿，肺活量', null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (3, '赛艇', '6-80岁', '大腿、肺活量', null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (4, '羽毛球', null, null, null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (6, '篮球', null, null, null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (7, '足球', null, null, null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (8, '拳击', null, null, null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (9, '柔道', null, null, '123');
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (10, '乒乓球', null, null, null);
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (11, '放风筝', '5-60', '舒展筋骨，吐故纳新，促进人体新陈代谢，改善血液循环', '放风筝在中国有悠久的历史。“纸花如雪满天飞，娇女秋千打四围。五色罗裙风摆动，好将蝴蝶斗春归。”郑板桥的诗歌，形象地描述了江苏扬州春季放风筝的景象。

放风筝要讲究天时地利。4月初，中国大部分地区气温适宜，有比较稳定的上升气流，风筝很容易升空。据专家研究，一般风力在2—5 级时，比较适宜风筝放飞。当然如果遇到雾霾或是沙尘天气，就最好不要逞能了。春天的风向、风速多变，要根据风速和风向变化决定是否放线、调整拉线的方向。此外，在地点的选择上，最好在公园等视野比较开阔、没有障碍物的空旷地带。

放风筝时，手牵引线，来回奔跑，有张有弛，手臂、腰部及腿部的肌肉可以得到锻炼。古人认为，放风筝的最高境界是“放中相牵，一线相连”。放飞后，要精神抖擞，把线看作缰绳，如同驯马一般，然后望天入静，随风筝飘移而前后奔走。

![放风筝.jpg](http://qrn83ytxn.hn-bkt.clouddn.com/ae9ab6f6-d306-49f9-a398-916293ee016ejpg)');
INSERT INTO `20220516_sport`.sport (id, name, applicable_age, beneficial_position, introduction) VALUES (12, '写代码', '8-50', '大脑，手指', '### 1、逻辑思维能力
因为程序就是对现实事物的抽象，而且，按照写好的逻辑运行。所以，编程能够锻炼我们的抽象思维能力和逻辑思维能力。
![9c16fdfaaf51f3deff77bb309beef01f3b297998.jpg](http://qrn83ytxn.hn-bkt.clouddn.com/765daa60-1eda-4d66-9aab-1524134202d0jpg)
### 2、提高效率
通过编程，我们能够很容易的处理大量重复性、低效率的工作，从而节省时间。而工作中很多地方都用到了编程来提高生产力，比如 Excel 中的宏，很多 Adobe 的产品都能使用 JavaScrpt 来自动化工作。
![574e9258d109b3defd85e73cc3bf6c81800a4caf.jpg](http://qrn83ytxn.hn-bkt.clouddn.com/77df30df-392d-4ad3-9e76-d4fe0ba17c31jpg)
### 3、 改变粗心的毛病
在编程的世界里，你多输入一个标点符号，都会造成程序大乱，无法运行，更别说在逻辑分析上出现问题。
### 4、避免被人工智能时代所淘汰，知道未来需要什么样的能力
前面已经说过了，编程是通向未来的语言，通过学会编程和智能机器沟通。不得不承认，现代社会已经步入信息化科技时代，传统行业正在被科技革新。
![b3119313b07eca8044c03bb39e2397dda0448398.jpg](http://qrn83ytxn.hn-bkt.clouddn.com/a68fefe2-f2e9-47c1-a276-de4e6cd35a70jpg)
```java
	//来一段大家最喜欢的代码
        async findPage() {
            this.$ajax.post(''/sport/findPage'', this.queryInfo).then((res) => {
                if (!res.data.flag) return this.$message.error(res.data.message);
                //给列表赋值
                this.tableData = res.data.data.rows;
                this.total = res.data.data.total;
                //console.log(this.tableData);
                for (let i=0; i<this.tableData.length; i++) {
                    this.tableData[i].introduction = marked(this.tableData[i].introduction);
                }
            }).catch((err) => {
                console.log(err.message);
                if (err.request.status == 400) return this.$message.warning(''请求错误！'');
                if (err.request.status == 500) return this.$message.warning(''数据请求失败，请稍后再试！'');
            });
        }

```
');