<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Relations\Pivot;

class ConversationUser extends Pivot
{
    public $incrementing=true;
    public $table='conversation_user';

    protected $fillable = [
        'conversation_id','user_id','read',
    ];

    public function user(){
        return $this->belongsTo('App\User','user_id');
    }

    public function conversation(){
        return $this->belongsTo('App\Models\Conversation','conversation_id');
    }
    
}
