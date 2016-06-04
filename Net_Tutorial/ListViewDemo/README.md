# ListViewDemo
<br \>
<br \>
# Screenshot
![Xcode indent settings](https://github.com/rocooshiang/LearningAndroidRecord/blob/ModifyBranch/Net_Tutorial/ListViewDemo/Screenshot/Image.png)

<br \>
<br \>

# Features
* 使用ListView呈現清單
* 藉由第三方Library(Picasso)來處理非同步的圖片下載
* ViewHolder是為了防止row layout重複的create

<br \>
<br \>

# Notes 
Android ListView與iOS UITableView是相同的功能，但是在row layout的重複使用機制有些微的不同:
##### ListView
用ViewHolder(view.setTag(holder) & holder = (ViewHolder) convertView.getTag())來達到重複使用已建立過的row layout，而且保證是對應到之前在該position建立的row layout
##### UITableView
會將create過的row丟到一個pool，當要使用時會去pool找一個出來，但是不一定是對應到原來的row，意思就是每次都要在重新設定該row上有用到的property

<br \>
<br \>

# Reference 
* [Raywenderlich Android ListView Tutorial](https://www.raywenderlich.com/124438/android-listview-tutorial)
* [Picasso](http://square.github.io/picasso/)
