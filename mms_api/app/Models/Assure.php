<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Assure extends Model
{

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'etat','profession',
        'employeur' ,
    ];

    public function user(){
        return $this->morphOne('App\User','usereable');
    }

    public function contrats(){
        return $this->hasMany('App\Models\Contrat');
    }

 
}
