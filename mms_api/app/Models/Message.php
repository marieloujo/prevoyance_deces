<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Message extends Model
{
    public $incrementing=true;
    public $table='messages';

    protected $fillable = [
        'body','notification','conversation_id',
    ];
        
    public function conversation(){
        return $this->belongsTo('App\Models\Conversation','conversation_id');
    }
}
