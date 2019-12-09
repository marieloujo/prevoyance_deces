<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Conversation extends Model
{
    public $incrementing=true;
    public $table='conversations';

    protected $fillable = [
        'id'
    ];
    

    public function messages(){
        return $this->hasMany('App\Models\Message');
    }

    public function conversation_users(){
        return $this->hasMany('App\Models\ConversationUser');
    }

    public function users(){
        return $this->belongsToMany('App\User','conversation_user','conversation_id','user_id')->using('App\Models\ConversationUser')->withPivot([
            'read',
        ])->withTimestamps();
    }

}
