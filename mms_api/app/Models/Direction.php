<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Direction extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'role_id','commission',
    ];


    public function user(){
        return $this->morphOne('App\User','usereable');
    }

    public function role(){
        return $this->belongsTo('App\Models\Role');
    }

}
