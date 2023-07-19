package uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.madels

import java.io.Serializable

class User:Serializable {
    var id:String?=null
    var fullName:String?=null
    var image:String?=null

    constructor(id: String?, fullName: String?, image: String?) {
        this.id = id
        this.fullName = fullName
        this.image = image

    }

}