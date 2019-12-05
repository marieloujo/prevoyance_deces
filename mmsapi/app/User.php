<?php

namespace App;

use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;

class User extends Authenticatable
{

    use HasApiTokens, Notifiable;
    protected $morphClass = 'App\User'; 
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nom','prenom','telephone','adresse','sexe','date_naissance','situation_matrimoniale','prospect', 'actif', 'login', 'commune_id','userable_id', 'userable_type','email', 'password',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function userable(){
        return $this->morphTo();
    }

    public function messages(){
        return $this->belongsToMany('App\User','messages','from_user_id','to_user_id')->using('App\Models\Message')->withPivot([
            'body','read','notification',
        ])->withTimestamps();
    }

    public function sms(){
        return $this->hasMany('App\Models\Message','from_user_id');
    }

    public function commune(){
        return $this->belongsTo('App\Models\Commune');
    }

    /** 
     * Find the user instance for the given telephone. 
     *  @param string $username 
     *  @return \App\User 
    */
    // public function findForPassport($username) {
    //     return $this->where('login', $username)->first(); 
    // }


    public function findForPassport($identity) {
        $columnName = filter_var($identity, FILTER_VALIDATE_INT)
            ? 'telephone' : 'login';
        return $this->where($columnName, $identity)->first();
    }


}
