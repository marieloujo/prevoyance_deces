<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\Pivot;

class Message extends Pivot
{
    public $incrementing=true;
    public $table='messages';

    protected $fillable = [
        'body','notification','read_at','from_user_id','to_user_id',
    ];
        
    public function from(){
        return $this->belongsTo('App\User','from_user_id');
    }

        
    public function to(){
        return $this->belongsTo('App\User','to_user_id');
    }
}
