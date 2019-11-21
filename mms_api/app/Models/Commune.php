<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Commune extends Model
{

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nom','departement_id',
    ];

    public function departement(){
        return $this->belongsTo('App\Models\Departement');
    }
    public function users(){
        return $this->hasMany('App\Models\User');
    }
}