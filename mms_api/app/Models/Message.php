<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\Pivot;

class Message extends Pivot
{
    public $incrementing=true;
    public $table='messages';

    protected $fillable = [
        'body','read_at','from_user_id','to_user_id',
    ];
}
